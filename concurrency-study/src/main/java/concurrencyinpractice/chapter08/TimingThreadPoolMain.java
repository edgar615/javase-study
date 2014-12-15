package concurrencyinpractice.chapter08;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2014/12/15.
 */
public class TimingThreadPoolMain {

    public static void main(String[] args) {
        TimingThreadPool timingThreadPool = new TimingThreadPool(3, 5, 100, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        for (int i = 0; i < 10; i ++) {
            timingThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                }
            });
        }
        timingThreadPool.shutdown();
    }
}
