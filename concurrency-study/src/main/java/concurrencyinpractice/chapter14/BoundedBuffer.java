package concurrencyinpractice.chapter14;

/**
 * Created by Administrator on 2014/12/17.
 */
public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {
    public BoundedBuffer(int capacity) {
        super(capacity);
    }

    public synchronized void put(V v) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        doPut(v);
        notifyAll();
    }

    public synchronized V take() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        V v = doTake();
        notifyAll();
        return v;
    }
}
