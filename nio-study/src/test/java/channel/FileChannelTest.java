package channel;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2014/11/20.
 */
public class FileChannelTest {

    @Test
    public void testRead() throws IOException {
        RandomAccessFile file = new RandomAccessFile("random.log", "rw");
        FileChannel channel = file.getChannel();
        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        //将数据写入缓冲区
        int bytesRead = channel.read(byteBuffer);
        while (bytesRead != -1) {
            //反转缓冲区，将Buffer从写模式切换到读模式
            byteBuffer.flip();
            while(byteBuffer.hasRemaining()){
                //一次读取一个字节
                System.out.print((char) byteBuffer.get());
            }

            byteBuffer.clear();
            bytesRead = channel.read(byteBuffer);
        }
        channel.close();
        file.close();
    }

    @Test
    public void testWrite() throws IOException {
        String newData = "New String to write to file..." + System.currentTimeMillis();
        FileOutputStream out = new FileOutputStream("filechannel.log");
        FileChannel channel = out.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        byteBuffer.clear();
        byteBuffer.put(newData.getBytes());

        byteBuffer.flip();

        while (byteBuffer.hasRemaining()) {
            channel.write(byteBuffer);
        }
        out.close();
        channel.close();
    }
}
