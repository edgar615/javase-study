package concurrencyinpractice.chapter14;

/**
 * Created by Administrator on 2014/12/17.
 */
public class BoundedBuffer2<V> extends BaseBoundedBuffer<V> {
    public BoundedBuffer2(int capacity) {
        super(capacity);
    }

    public synchronized void put(V v) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        boolean wasEmpty = isEmpty();
        doPut(v);
        if (wasEmpty) {
            notifyAll();
        }
    }

    public synchronized V take() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        boolean wasFull = isFull();
        V v = doTake();
        if (wasFull) {
            notifyAll();
        }
        return v;
    }
}
