package concurreny.blockingqueue;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Administrator on 2014/11/25.
 */
public class Consumer implements Runnable {

    private BlockingQueue queue;

    public Consumer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            System.out.println(queue.take());
            Thread.sleep(1500);
            System.out.println(queue.take());
            Thread.sleep(1500);
            System.out.println(queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
