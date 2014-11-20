package bytes;

import org.junit.Test;

import java.io.*;

/**
 * Created by Administrator on 2014/11/19.
 */
public class PrintStreamTest {

    @Test
    public void test() throws IOException {
        OutputStream out = new FileOutputStream("out.log");
        out.write(1);
        out.write(2);
        out.close();
    }

    @Test
    public void test2() throws IOException {
        PrintStream out = new PrintStream(new FileOutputStream("out2.log"));
        out.print(1);
        out.print(2);
        out.close();
    }
}
