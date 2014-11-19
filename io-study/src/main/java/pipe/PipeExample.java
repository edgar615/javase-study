package pipe;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.TimeUnit;

/**
 * read和write要在不同的线程上，否则会引起死锁
 */
public class PipeExample {
    public static void main(String[] args) throws IOException {
        final PipedOutputStream pipedOutputStream = new PipedOutputStream();
        final PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pipedOutputStream.write("Hello world, pipe!".getBytes());
                } catch (IOException e) {
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int data = pipedInputStream.read();
                    while (data != -1) {
                        System.out.print((char) data);
                        data = pipedInputStream.read();
                    }
                } catch (IOException e) {
                }
            }
        });
        thread1.start();
        thread2.start();
    }
}
