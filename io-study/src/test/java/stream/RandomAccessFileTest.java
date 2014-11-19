package stream;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Edgar on 14-11-15.
 */
public class RandomAccessFileTest {

    @Test
    public void testRandomRead() throws IOException {
        RandomAccessFile file = new RandomAccessFile("random.log", "rw");
        file.seek(50);
        int data = file.read();
        while (data != -1) {
            System.out.print((char) data);
            data = file.read();
        }
    }

    @Test
    public void testRandomWrite() throws IOException {
        RandomAccessFile file = new RandomAccessFile("random.log", "rw");
        file.seek(50);
        char[] chars = "[Hello world!]".toCharArray();
        for (int i = 0; i < chars.length; i ++) {
            file.write(chars[i]);
        }
        file.close();
    }

    @Test
    public void testRandomReadSkip() throws IOException {
        RandomAccessFile file = new RandomAccessFile("random.log", "rw");
        int data = file.read();
        int skip = 0;
        while (data != -1) {
            System.out.print((char) data);
            skip += 5;
            file.seek(skip);
            data = file.read();
        }
    }
}
