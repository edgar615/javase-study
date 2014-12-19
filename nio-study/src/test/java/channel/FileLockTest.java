package channel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2014/12/19.
 */
public class FileLockTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                test();
            }
        };
        t.start();
        TimeUnit.SECONDS.sleep(3);
        test();
    }

    private static void test() {
        try {
            String filename = "blahblah.txt";
            RandomAccessFile raf = new RandomAccessFile(filename, "r");
            FileChannel fileChannel = raf.getChannel();
            FileLock fileLock = fileChannel.lock(0L, Long.MAX_VALUE, true);
            try {
                System.out.println(fileLock.isShared());
                TimeUnit.SECONDS.sleep(10);
            } finally {
                fileLock.release();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
