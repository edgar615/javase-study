package chars;

import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by Administrator on 2014/11/20.
 */
public class StringWriterTest {
    @Test
    public void testWriter() throws IOException {
        StringWriter writer = new StringWriter();
        writer.write("The StringWriter class enables you to obtain the characters written to a Writer as a String.");
        System.out.println(writer.toString());
        System.out.println(writer.getBuffer());
    }
}
