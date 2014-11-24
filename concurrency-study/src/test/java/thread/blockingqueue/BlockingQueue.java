package thread.blockingqueue;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2014/11/24.
 */
public class BlockingQueue {

    private List queue = new LinkedList();

    private int limit = 10;

    public BlockingQueue(int limit) {

        this.limit = limit;

    }

    public synchronized void enqueue(Object element) throws InterruptedException {
        while (queue.size() == limit) {
            wait();
        }
        //只有队列为空的时候取数据的线程才会调用wait，其他情况不需要唤醒线程
        if (queue.size() == 0) {
            notifyAll();
        }
        this.queue.add(element);
    }

    public synchronized Object dequeue()

            throws InterruptedException {

        while (this.queue.size() == 0) {

            wait();

        }

        //只有队列满的时候放数据的线程才会调用wait，其他情况不需要唤醒线程
        if (this.queue.size() == this.limit) {

            notifyAll();

        }

        return this.queue.remove(0);

    }
}
