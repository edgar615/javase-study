package stream;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * The ByteArrayOutputStream class of the Java IO API allows you to capture data written to a stream in an array.
 */
public class ByteOutputStreamTest {

    @Test
    public void testWriteInt() throws IOException {
        byte[] bytes = "The ByteArrayOutputStream class of the Java IO API allows you to capture data written to a stream in an array.".getBytes("utf-8");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (int i = 0; i < bytes.length; i ++) {
            out.write(bytes[i]);
        }
        System.out.println(out.toString());
        System.out.println(new String(out.toByteArray()));
        System.out.println(out.toString("UTF-8"));
    }
    @Test
    public void testWriteBytes() throws IOException {
        byte[] bytes = "The ByteArrayOutputStream class of the Java IO API allows you to capture data written to a stream in an array.".getBytes("utf-8");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(bytes);
        System.out.println(out.toString());
        System.out.println(new String(out.toByteArray()));
        System.out.println(out.toString("UTF-8"));
    }

    @Test
    public void testWriteOffLen() throws IOException {
        byte[] bytes = "The ByteArrayOutputStream class of the Java IO API allows you to capture data written to a stream in an array.".getBytes("utf-8");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(bytes, 0, 5);
        System.out.println(out.toString());
        System.out.println(new String(out.toByteArray()));
        System.out.println(out.toString("UTF-8"));
    }

}
