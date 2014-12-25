package chapter01;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2014/12/24.
 */
public class SocketChannelExample {
    public static void main(String[] args) throws IOException,
            InterruptedException {
        int port = 1234;

        SocketChannel channel = SocketChannel.open();

        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress("127.0.0.1", port));

        while (!channel.finishConnect()) {
            // wait
        }

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

//            ByteBuffer buffer1 = ByteBuffer.allocate(30);
//            buffer1.put("I am Edgar".getBytes());
//            buffer1.flip();
//            channel.write(buffer1);
//            buffer1.compact();
//            TimeUnit.SECONDS.sleep(5);

            if (count < 0) {
                channel.close();
            }
        }
    }
}
