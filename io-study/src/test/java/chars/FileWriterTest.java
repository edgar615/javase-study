package chars;

import org.junit.Test;

import java.io.*;

/**
 * Created by Administrator on 2014/11/20.
 */
public class FileWriterTest {
    @Test
    public void testWrite() throws IOException {
        Writer writer = new FileWriter("writer.out");
        writer.write("Hello world");
        writer.flush();
        writer.close();
    }
}
