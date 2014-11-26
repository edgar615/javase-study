package concurreny.semaphore;

import java.util.concurrent.Semaphore;

/**
 * Created by Administrator on 2014/11/26.
 */
public class PrintQueue {
    private final Semaphore semaphore = new Semaphore(1);

    public void printJob (Object document){
        try {
            semaphore.acquire();
            long duration=(long)(Math.random()*10);
            System.out.printf("%s: PrintQueue: Printing a Job during %d seconds\n",Thread.currentThread().getName(),duration);
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}
