package chars;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * Created by Administrator on 2014/11/20.
 */
public class LineNumberReaderTest {
    @Test
    public void testRead() throws IOException {
        LineNumberReader reader = new LineNumberReader(new FileReader("linnumber-reader.log"));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            System.out.println(reader.getLineNumber());
        }
        reader.close();
    }
}
