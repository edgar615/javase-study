package concurreny.blockingqueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2014/11/25.
 */
public class DelayQueueConsumer implements Runnable {

    private final BlockingQueue<Delayed> queue;

    public DelayQueueConsumer(BlockingQueue<Delayed> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Delayed delayed = queue.take();
                System.out.printf("[%s] - Take object = %s%n",
                        Thread.currentThread().getName(), delayed);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
