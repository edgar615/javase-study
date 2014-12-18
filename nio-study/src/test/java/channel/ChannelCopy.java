package channel;

import java.io.IOException;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Created by Administrator on 2014/12/18.
 */
public class ChannelCopy {

    public static void main(String[] args) throws IOException {
        ReadableByteChannel source = Channels.newChannel(System.in);
        WritableByteChannel dest = Channels.newChannel(System.out);

        channelCopy1(source, dest);

        source.close();
        dest.close();

    }

    private static void channelCopy1(ReadableByteChannel source, WritableByteChannel dest) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16 * 1024);
        while (source.read(buffer) != -1) {
            //翻转
            buffer.flip();
            dest.write(buffer);
            //清除已经读取的数据
            buffer.compact();
        }

        //再次读取buffer，确保所有的数据都被读取
        buffer.flip();
        while (buffer.hasRemaining()) {
            dest.write(buffer);
        }

    }

    private static void channelCopy2(ReadableByteChannel source, WritableByteChannel dest) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16 * 1024);
        while (source.read(buffer) != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                dest.write(buffer);
            }
            //清空buffer
            buffer.clear();
        }
    }
}
