package chars;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;

/**
 * The CharArrayWriter makes it possible to write characters to a Writer and convert the written characters into a char array.
 */
public class CharArrayWriterTest {

    @Test
    public void testWriteInt() throws IOException {
        char[] chars = "The CharArrayWriter makes it possible to write characters to a Writer and convert the written characters into a char array.".toCharArray();
        CharArrayWriter writer = new CharArrayWriter();
        for (int i = 0; i < chars.length; i ++) {
            writer.write(chars[i]);
        }
        System.out.println(writer.toString());
        System.out.println(new String(writer.toCharArray()));
    }
    @Test
    public void testWriteChars() throws IOException {
        char[] chars = "The CharArrayWriter makes it possible to write characters to a Writer and convert the written characters into a char array.".toCharArray();
        CharArrayWriter writer = new CharArrayWriter();
        writer.write(chars);
        System.out.println(writer.toString());
        System.out.println(new String(writer.toCharArray()));
    }

    @Test
    public void testWriteOffLen() throws IOException {
        char[] chars = "The CharArrayWriter makes it possible to write characters to a Writer and convert the written characters into a char array.".toCharArray();
        CharArrayWriter writer = new CharArrayWriter();
        writer.write(chars, 0, 5);
        System.out.println(writer.toString());
        System.out.println(new String(writer.toCharArray()));
    }

}
