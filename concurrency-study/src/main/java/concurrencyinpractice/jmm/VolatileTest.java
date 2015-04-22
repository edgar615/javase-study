package concurrencyinpractice.jmm;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/4/16.
 */
public class VolatileTest {

    public static volatile int race = 0;

    private static final int THREAD_COUNT = 20;

    public static void inc() {
        race ++;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i ++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i ++) {
                        inc();
                    }
                    System.out.println(Thread.currentThread() + "complete");
                }
            });
            threads[i].start();
        }
//        while (Thread.activeCount() > 1) {
//            Thread.yield();
//        }
        TimeUnit.SECONDS.sleep(30);
        System.out.println(race);
    }
}
