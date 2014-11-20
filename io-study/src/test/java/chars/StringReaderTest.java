package chars;

import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Administrator on 2014/11/20.
 */
public class StringReaderTest {
    @Test
    public void testRead() throws IOException {
        StringReader reader = new StringReader("The StringReader class enables you to turn an ordinary String into a reader");
        int data = -1;
        while ((data = reader.read()) != -1) {
            System.out.print((char)data);
        }
        reader.close();
    }
}
