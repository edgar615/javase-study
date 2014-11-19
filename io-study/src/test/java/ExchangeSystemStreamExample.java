import java.io.*;

/**
 * Created by Administrator on 2014/11/4.
 */
public class ExchangeSystemStreamExample {
    public static void main(String[] args) {
        try {
            OutputStream outputStream = new FileOutputStream("system-out.log");
            System.setOut(new PrintStream(outputStream));
            System.out.println("File opened...");

        } catch (IOException e) {
            System.err.println("File opening failed:");
            e.printStackTrace();
        }
    }
}
