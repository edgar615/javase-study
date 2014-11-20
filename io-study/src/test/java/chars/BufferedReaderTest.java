package chars;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Created by Administrator on 2014/11/20.
 */
public class BufferedReaderTest {
    @Test
    public void testRead() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("buffered-reader.log"));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }
}
