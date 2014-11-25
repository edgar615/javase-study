package concurreny.blockingqueue;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2014/11/25.
 */
public class DelayQueueProducer implements Runnable {

    private final BlockingQueue<Delayed> queue;

    private final Random random = new Random();

    public DelayQueueProducer(BlockingQueue<Delayed> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i ++) {
            int delay = random.nextInt(10000);
            Delayed delayed = new DelayedElement<String>(UUID.randomUUID().toString(), delay);
            System.out.printf("Put object = %s%n", delayed);
            try {
                queue.put(delayed);
//                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
