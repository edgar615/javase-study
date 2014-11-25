package concurreny.blockingqueue;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Delayed;

/**
 * Created by Administrator on 2014/11/25.
 */
public class PriorityQueueProducer implements Runnable {

    private final BlockingQueue<PriorityElement> queue;

    private final Random random = new Random();

    public PriorityQueueProducer(BlockingQueue<PriorityElement> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i ++) {
            int delay = random.nextInt(10000);
            PriorityElement element = new PriorityElement(delay);
            System.out.printf("Put object = %s%n", element);
            try {
                queue.put(element);
//                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
