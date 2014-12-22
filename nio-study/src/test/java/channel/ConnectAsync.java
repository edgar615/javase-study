package channel;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by Administrator on 2014/12/22.
 */
public class ConnectAsync {

    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 1234;
        InetSocketAddress inetAddress = new InetSocketAddress(host, port);
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        System.out.println ("initiating connection");
        socketChannel.connect(inetAddress);

        while (!socketChannel.finishConnect()) {
            doSomethingUseful();
        }
        System.out.println ("connection established");

        ByteBuffer buffer = ByteBuffer.allocate(128);
        while (socketChannel.read(buffer) != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.print((char)buffer.get());
            }
            buffer.clear();

        }
        socketChannel.close();
    }

    private static void doSomethingUseful() {
        System.out.println ("doing something useless");
    }
}
