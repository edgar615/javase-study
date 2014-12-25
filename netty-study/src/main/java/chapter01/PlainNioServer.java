package chapter01;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2014/12/24.
 */
public class PlainNioServer {

    public static void main(String[] args) throws Exception {
        new PlainNioServer().serve(1234);
    }

    public void serve(int port) throws Exception {
        System.out.println("Listening for connections on port " + port);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(port);
        serverSocketChannel.bind(address);
        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            try {
                //返回值将会是已经确定就绪的通道的数目
                selector.select();
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
            //访问“已选择键集（selected key set）”中的就绪通道。
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                //Selector不会自己从已选择键集中移除SelectionKey实例。必须在处理完通道时自己移除。下次该通道变成就绪时，Selector会再次将其放入已选择键集中。
                iterator.remove();
                try {
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        //Accept the client connection
                        SocketChannel client = server.accept();
                        System.out.println("Accepted connection from " +
                                client);
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, ByteBuffer.allocate(100));
                        sayHello(client);
                    }
                    if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        //得到附加对象
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        //Read data to ByteBuffer 从通道写入数据到buffer
                        readDataFromSocket(client);
                    }
                    if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        //Write data from ByteBuffer to channel 通道从buffer读取数据
                        writeDataToSocket(client, buffer);
                    }
                } catch (IOException e) {
                    key.cancel();
                    try {
                      key.channel().close();
                    } catch (IOException ex) {

                    }
                }
            }
        }
    }

    private void writeDataToSocket(SocketChannel client, ByteBuffer buffer) throws IOException, InterruptedException {
        buffer.clear();
        buffer.put("who are you?".getBytes());
        buffer.flip();
        client.write(buffer);
        buffer.compact();
        TimeUnit.SECONDS.sleep(3);
    }

    protected static void readDataFromSocket(SocketChannel channel) throws Exception {
        int count;
        ByteBuffer buffer = ByteBuffer.allocate(100);

        while ((count = channel.read(buffer)) > 0) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.println("client say: " + Charset.defaultCharset().decode(buffer));
            }
            buffer.clear();
        }
        if (count < 0) {
            channel.close();
        }
    }

    private static void sayHello(SocketChannel channel) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(100);
        buffer.put("Hi there!\r\n".getBytes());
        buffer.flip();
        channel.write(buffer);
    }
}
