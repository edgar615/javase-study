package concurreny.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

public class DelayQueueExample {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue queue = new DelayQueue();

        Thread t1 = new Thread(new DelayQueueProducer(queue), "producer");
        Thread t2 = new Thread(new DelayQueueConsumer(queue), "consumer");
        t1.start();
        t2.start();
    }
}