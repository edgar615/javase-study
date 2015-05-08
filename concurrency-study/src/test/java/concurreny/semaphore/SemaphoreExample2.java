package concurreny.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2014/12/16.
 */
public class SemaphoreExample2 {

    public static void main(String[] args) throws InterruptedException {
        Semaphore available = new Semaphore(0);

        if (available.tryAcquire(5, TimeUnit.SECONDS)) {
            System.out.println("获取到信号量");
            available.release();
        } else {
            System.out.println("没获取到信号量");
        }

    }
}
