package chars;

import org.junit.Test;

import java.io.*;

/**
 * Created by Administrator on 2014/11/20.
 */
public class BufferedWriterTest {
    @Test
    public void testWriter() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("buffered-writer.log"));
        writer.write("Hello world");
        writer.newLine();
        writer.write("Hello world");
        writer.flush();
        writer.close();
    }
}
