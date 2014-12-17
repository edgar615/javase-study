package concurrencyinpractice.chapter14;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2014/12/17.
 */
public class ConditionBoundedBuffer<T> {
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private static final int BUFFER_SIZE = 100;
    private final T[] items = (T[]) new Object[BUFFER_SIZE];
    private int tail;
    private int head;
    private int count;

    public void put(T x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[tail] = x;
            if (++ tail == items.length) {
                tail = 0;
            }
            ++ count;
            notEmpty.notifyAll();
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            T x = items[head];
            items[head] = null;
            if (++ head == items.length) {
                head = 0;
            }
            -- count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }
}
