package thread.pool;

import thread.ThreadExample;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Edgar on 14-11-24.
 */
public class PoolThread extends Thread {

    private BlockingQueue<Runnable> queue = null;
    private boolean isStopped = false;

    public PoolThread(BlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!isStopped) {
            try {
                Runnable runnable = queue.take();
                runnable.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void toStop() {
        isStopped = true;
        this.interrupt(); // 打断池中线程的 dequeue() 调用.
    }

    public synchronized boolean isStopped() {
        return isStopped;
    }
}
