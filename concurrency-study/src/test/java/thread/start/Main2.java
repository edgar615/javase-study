package thread.start;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2014/11/25.
 */
public class Main2 {

    public static void main(String[] args) throws IOException, InterruptedException {
        PrimeGenerator primeGenerator = new PrimeGenerator();
        Thread t = new Thread(primeGenerator);
        t.start();
        TimeUnit.SECONDS.sleep(10);
        t.interrupt();
    }

}
