package io;

import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import org.junit.Test;

import java.io.*;
import java.util.List;

/**
 * Created by Administrator on 2015/2/10.
 */
public class CharStreamsTest {

    @Test
    public void testRead() throws IOException {
        Reader in = new InputStreamReader(new FileInputStream("D:/1.txt"));
        String s = CharStreams.toString(in);
        System.out.println(s);
    }

    @Test
    public void testReadLine() throws IOException {
        Reader in = new InputStreamReader(new FileInputStream("D:/1.txt"));
        List<String> lines = CharStreams.readLines(in);
        for (String line : lines) {
            System.out.println(line);
        }
    }

    @Test
    public void testCopy() throws IOException {
        Reader in = new InputStreamReader(new FileInputStream("D:/1.txt"));
        Appendable a = new StringBuilder();//writer
        CharStreams.copy(in, a);
        System.out.println(a);
    }

    @Test
    public void testSkipFully() throws IOException {
        Reader in = new InputStreamReader(new FileInputStream("D:/1.txt"));
        CharStreams.skipFully(in, 100);
        List<String> lines = CharStreams.readLines(in);
        for (String line : lines) {
            System.out.println(line);
        }
    }

    @Test
    public void testNullWriter() throws IOException {
        Reader in = new InputStreamReader(new FileInputStream("D:/1.txt"));
        Appendable a = new StringBuilder();
        CharStreams.copy(in, CharStreams.nullWriter());
        System.out.println(a);
    }
}
