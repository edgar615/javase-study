package bytes;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The ByteArrayInputStream class of the Java IO API allows you to read data from byte arrays as streams
 */
public class ByteInputStreamTest {

    @Test
    public void testDefault() throws IOException {
        byte[] bytes = "The ByteArrayInputStream class of the Java IO API allows you to read data from byte arrays as streams".getBytes("utf-8");
        //  创建一个 ByteArrayInputStream，使用 bytes 作为其缓冲区数组
        InputStream in = new ByteArrayInputStream(bytes);
        int data = in.read();
        while (data != -1) {
            System.out.print((char) data);
            data = in.read();
        }
        System.out.println();
    }

    @Test
    public void testReadByte() throws IOException {
        byte[] bytes = "The ByteArrayInputStream class of the Java IO API allows you to read data from byte arrays as streams".getBytes("utf-8");
        InputStream in = new ByteArrayInputStream(bytes);
        int length = 5;
        byte[] b = new byte[length];
        int readLength = in.read(b);
        while (readLength != -1) {
            System.out.print(new String(b, 0, readLength));
            readLength = in.read(b);
        }
        System.out.println();
    }

    @Test
    public void testReadByteOffLen() throws IOException {
        byte[] bytes = "The ByteArrayInputStream class of the Java IO API allows you to read data from byte arrays as streams".getBytes("utf-8");
        InputStream in = new ByteArrayInputStream(bytes);
        int length = 5;
        byte[] b = new byte[length];
        int readLength = in.read(b, 0, length);
        while (readLength != -1) {
            System.out.print(new String(b, 0, readLength));
            readLength = in.read(b, 0, length);
        }
        System.out.println();
    }

    @Test
    public void testReadByteOffLen2() throws IOException {
        byte[] bytes = "The ByteArrayInputStream class of the Java IO API allows you to read data from byte arrays as streams".getBytes("utf-8");
        int offset = 0;
        int length = 5;
        InputStream in = new ByteArrayInputStream(bytes, 0, length);
        int data = in.read();
        while (data != -1) {
            System.out.print((char) data);
            data = in.read();
        }
        System.out.println();
    }

}
