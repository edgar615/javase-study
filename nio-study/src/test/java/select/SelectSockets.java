package select;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * Created by Administrator on 2014/12/23.
 */
public class SelectSockets {
    public static int PORT_NUMBER = 1234;

    public static void main(String[] args) throws Exception {
        int port = PORT_NUMBER;
        System.out.println("Listening on port " + port);
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ServerSocket serverSocket = ssc.socket();
        Selector selector = Selector.open();

        serverSocket.bind(new InetSocketAddress(port));
        ssc.configureBlocking(false);
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int n = selector.select();
            if (n == 0) {
                continue;
            }

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    registerChannel(selector, socketChannel, SelectionKey.OP_READ);
                    sayHello(socketChannel);

                }

                if (key.isReadable()) {
                    readDataFromSocket(key);
                }

                iterator.remove();
            }
        }
    }

    protected static void registerChannel(Selector selector, SelectableChannel channel, int ops) throws IOException {
        if (channel == null) {
            return;
        }
        channel.configureBlocking(false);
        channel.register(selector, ops);
    }

    private static void sayHello(SocketChannel channel) throws Exception {
        buffer.clear();
        buffer.put("Hi there!\r\n".getBytes());
        buffer.flip();
        channel.write(buffer);
    }

    private static ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

    protected static void readDataFromSocket(SelectionKey key) throws Exception {
        SocketChannel channel = (SocketChannel) key.channel();
        int count;
        buffer.clear();

        while ((count = channel.read(buffer)) > 0) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                channel.write(buffer);
            }
            buffer.clear();
        }
        if (count < 0) {
            channel.close();
        }
    }
}
