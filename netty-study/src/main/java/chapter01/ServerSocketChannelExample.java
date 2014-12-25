package chapter01;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2014/12/24.
 */
public class ServerSocketChannelExample {
    private static String clientChannel = "clientChannel";
    private static String serverChannel = "serverChannel";
    private static String channelType = "channelType";

    public static void main(String[] args) throws IOException {
        new ServerSocketChannelExample().serve(1234);
    }

    public void serve(int port) throws IOException {
        System.out.println("Listening for connections on port " + port);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(port);
        serverSocketChannel.bind(address);
        serverSocketChannel.configureBlocking(false);

        Map<String, String> properties = new HashMap<String, String>();
        properties.put(channelType, serverChannel);

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, properties);

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
                Map<String, String> attachment = (Map<String, String>) key.attachment();
                if (serverChannel.equals(attachment.get(channelType))) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel client = server.accept();
                    if (client != null) {
                        System.out.println("Accepted connection from " +
                                client);
                        client.configureBlocking(false);
                        SelectionKey clientKey = client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        Map<String, String> clientProperties = new HashMap<String, String>();
                        clientProperties.put(channelType, clientChannel);
                        clientKey.attach(clientProperties);
                        ByteBuffer buffer = ByteBuffer.allocate(100);
                        buffer.put("Hello".getBytes());
                        while (buffer.hasRemaining()) {
                            client.write(buffer);
                        }
                        buffer.clear();
                    }
                } else {
                    ByteBuffer buffer = ByteBuffer.allocate(100);
                    SocketChannel client = (SocketChannel) key.channel();
                    int bytesRead = 0;
                    if (key.isReadable()) {
                        if ((bytesRead = client.read(buffer)) > 0) {
                            buffer.flip();
                            System.out.println(Charset.defaultCharset().decode(
                                    buffer));
                            buffer.clear();
                        }
                    }
                }
//                try {
//                    if (key.isAcceptable()) {
//                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
//                        //Accept the client connection
//                        SocketChannel client = server.accept();
//                        System.out.println("Accepted connection from " +
//                                client);
//                        client.configureBlocking(false);
//                        client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, ByteBuffer.allocate(100));
//                    }
//                    if (key.isReadable()) {
//                        SocketChannel client = (SocketChannel) key.channel();
//                        //得到附加对象
//                        ByteBuffer buffer = (ByteBuffer) key.attachment();
//                        //Read data to ByteBuffer 从通道写入数据到buffer
//                        client.read(buffer);
//                    }
//                    if (key.isWritable()) {
//                        SocketChannel client = (SocketChannel) key.channel();
//                        ByteBuffer buffer = (ByteBuffer) key.attachment();
//                        //Write data from ByteBuffer to channel 通道从buffer读取数据
//                        buffer.flip();
//                        client.write(buffer);
//                        buffer.compact();
//                    }
//                } catch (IOException e) {
//                    key.cancel();
//                    try {
//                      key.channel().close();
//                    } catch (IOException ex) {
//
//                    }
//                }
            }
        }
    }
}
