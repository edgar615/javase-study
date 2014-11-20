package chars;

import org.junit.Test;

import java.io.*;

/**
 * Created by Administrator on 2014/11/20.
 */
public class FileReaderTest {
    @Test
    public void testRead() throws IOException {
        Reader reader = new FileReader("inputread.log");
        int data = -1;
        while ((data = reader.read()) != -1) {
            System.out.print((char) data);
        }
        reader.close();
    }
}
