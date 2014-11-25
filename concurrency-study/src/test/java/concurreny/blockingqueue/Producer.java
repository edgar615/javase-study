package concurreny.blockingqueue;


import java.util.concurrent.BlockingQueue;

/**
 * Created by Administrator on 2014/11/25.
 */
public class Producer implements Runnable {

    private BlockingQueue queue;

    public Producer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            queue.put("1");
            System.out.println("put 1");
            Thread.sleep(1000);
            queue.put("2");
            System.out.println("put 2");
            Thread.sleep(1000);
            queue.put("3");
            System.out.println("put 3");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
