import org.junit.Test;

import java.io.CharArrayReader;
import java.io.IOException;

/**
 * Created by Administrator on 2014/11/10.
 */
public class CharArrayReaderTest {
    @Test
    public void testDefault() throws IOException {
        char[] chars = "The CharArrayReader class enables you to read the contents of a char array as a character stream".toCharArray();
        CharArrayReader reader = new CharArrayReader(chars);
        int data = reader.read();
        while (data != -1) {
            System.out.print((char) data);
            data = reader.read();
        }
        System.out.println();
    }

    @Test
    public void testReadChars() throws IOException {
        char[] chars = "The CharArrayReader class enables you to read the contents of a char array as a character stream".toCharArray();
        CharArrayReader reader = new CharArrayReader(chars);
        int length = 5;
        char[] b = new char[length];
        int readLength = reader.read(b);
        while (readLength != -1) {
            System.out.print(new String(b, 0, readLength));
            readLength = reader.read(b);
        }
        System.out.println();
    }

    @Test
    public void testReadCharsOffLen() throws IOException {
        char[] chars = "The CharArrayReader class enables you to read the contents of a char array as a character stream".toCharArray();
        CharArrayReader reader = new CharArrayReader(chars);
        int length = 5;
        char[] b = new char[length];
        int readLength = reader.read(b, 0, length);
        while (readLength != -1) {
            System.out.print(new String(b, 0, readLength));
            readLength = reader.read(b, 0, length);
        }
        System.out.println();
    }

    @Test
    public void testReadCharsOffLen2() throws IOException {
        char[] chars = "The CharArrayReader class enables you to read the contents of a char array as a character stream".toCharArray();
        CharArrayReader reader = new CharArrayReader(chars, 0, 5);
        int data = reader.read();
        while (data != -1) {
            System.out.print((char) data);
            data = reader.read();
        }
        System.out.println();
    }
}
