package concurreny.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueExample {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue queue = new SynchronousQueue();

        Thread t1 = new Thread(new PriorityQueueProducer(queue), "producer");
        Thread t2 = new Thread(new PriorityQueueConsumer(queue), "consumer");
        t1.start();
        t2.start();
    }
}