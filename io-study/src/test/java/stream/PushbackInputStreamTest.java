package stream;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2014/11/19.
 */
public class PushbackInputStreamTest {
    @Test
    public void test() throws IOException, InterruptedException {

        PushbackInputStream in = new PushbackInputStream(new FileInputStream("PushbackInputStream.log"));
        int data = in.read();
        while (data != -1) {
            if (data == 'a') {
                in.unread(data);
            } else {
                System.out.print((char) data);
                TimeUnit.SECONDS.sleep(1);
                data = in.read();
            }
        }
    }

    @Test
    public void test2() throws IOException, InterruptedException {

        //指定推回缓冲区的大小为8
        PushbackInputStream in = new PushbackInputStream(new FileInputStream("PushbackInputStream.log"), 8);
        int data = in.read();
        while (data != -1) {
            if (data == 'a') {
                System.out.print((char) data);
                in.unread(data);
            } else {
                System.out.print((char) data);
                TimeUnit.SECONDS.sleep(1);
                data = in.read();
            }
        }
    }
}
