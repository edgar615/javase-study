package io;

import com.google.common.io.ByteStreams;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2015/2/10.
 */
public class ByteStreamsTest {

    @Test
    public void testRead() throws IOException {
        FileInputStream in = new FileInputStream("D:/1.txt");
        byte[] bytes = ByteStreams.toByteArray(in);
        for (int i = 0; i < bytes.length; i ++) {
            System.out.print((char) bytes[i]);
        }
    }

    @Test
    public void testCopy() throws IOException {
        FileInputStream in = new FileInputStream("D:/1.txt");
        FileOutputStream out = new FileOutputStream("D:/2.txt");
        ByteStreams.copy(in, out);
    }

    @Test
    public void testReadFully() throws IOException {
        FileInputStream in = new FileInputStream("D:/1.txt");
        byte[] bytes = new byte[10];
        ByteStreams.readFully(in, bytes);
        for (int i = 0; i < bytes.length; i ++) {
            System.out.print((char) bytes[i]);
        }
    }

    @Test
    public void testSkipFully() throws IOException {
        FileInputStream in = new FileInputStream("D:/1.txt");
        byte[] bytes = new byte[10];
        ByteStreams.skipFully(in, 10);
        ByteStreams.readFully(in, bytes);
        for (int i = 0; i < bytes.length; i ++) {
            System.out.print((char) bytes[i]);
        }
    }

    @Test
    public void testNullOutputStream() throws IOException {
        FileInputStream in = new FileInputStream("D:/1.txt");
        ByteStreams.copy(in, ByteStreams.nullOutputStream());
    }
}
