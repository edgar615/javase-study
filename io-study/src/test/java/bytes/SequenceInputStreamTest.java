package bytes;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.SequenceInputStream;

/**
 * Created by Administrator on 2014/11/19.
 */
public class SequenceInputStreamTest {
    @Test
    public void test() throws IOException {
        SequenceInputStream in = new SequenceInputStream(new FileInputStream("file1.log"), new FileInputStream("file2.log"));
        int data = in.read();
        while (data != -1) {
            System.out.print((char) data);
            data = in.read();
        }
    }
}
