package concurrencyinpractice.chapter12;

import concurrencyinpractice.annotation.GuardedBy;

import java.util.concurrent.Semaphore;

/**
 * Created by Administrator on 2014/12/16.
 */
public class BoundedBuffer<E> {
    private final Semaphore availableItems;
    private final Semaphore availableSpaces;

    @GuardedBy("this")
    private final E[] items;

    @GuardedBy("this")
    private int putPoisition = 0;
    @GuardedBy("this")
    private int takePoisition = 0;

    public BoundedBuffer(int capacity) {
        availableItems = new Semaphore(0);
        availableSpaces = new Semaphore(capacity);
        items = (E[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return availableItems.availablePermits() == 0;
    }

    public boolean isFull() {
        return availableSpaces.availablePermits() == 0;
    }

    public void put(E x) throws InterruptedException {
        availableSpaces.acquire();
        doInsert(x);
        availableItems.release();
    }

    public E take() throws InterruptedException {
        availableItems.acquire();
        E item = doExtract();
        availableSpaces.release();
        return item;
    }

    private E doExtract() {
        int i = takePoisition;
        E x = items[i];
        items[i] = null;
        takePoisition = (++i == items.length) ? 0 : i;
        return x;
    }

    private synchronized void doInsert(E x) {
        int i = putPoisition;
        items[i] = x;
        putPoisition = (++i == items.length) ? 0 : i;
    }
}
