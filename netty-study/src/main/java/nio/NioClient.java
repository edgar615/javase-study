package nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2015/1/28.
 */
public class NioClient {

    static final int remotePort = Integer.parseInt(System.getProperty("port", "8088"));
    static final String remoteHost = System.getProperty("host", "127.0.0.1");

    public static void main(String[] args) throws IOException {
        new NioClient().run(remoteHost, remotePort);
    }

    public void run(String host, int port) throws IOException {

        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress(host, port));

        while (!channel.finishConnect()) {
            //wait
        }
        System.out.println("connected");

        while (true) {
            ByteBuffer buffer = ByteBuffer.allocate(100);
            int count;
            while ((count = channel.read(buffer)) > 0) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    System.out.println("server say: " + Charset.defaultCharset().decode(buffer));
                }
                buffer.clear();
            }
            if (count < 0) {
                channel.close();
            }
        }
    }
}
