package concurreny.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2014/11/25.
 */
public class PriorityQueueConsumer implements Runnable {

    private final BlockingQueue<PriorityElement> queue;

    public PriorityQueueConsumer(BlockingQueue<PriorityElement> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                PriorityElement element = queue.take();
                System.out.printf("[%s] - Take object = %s%n",
                        Thread.currentThread().getName(), element);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
