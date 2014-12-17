package concurrencyinpractice.chapter14;

/**
 * Created by Administrator on 2014/12/17.
 */
public class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V> {
    protected GrumpyBoundedBuffer(int capacity) {
        super(capacity);
    }

    public synchronized void put(V v) {
        if (isFull()) {
            throw new BufferFullException();
        }
        doPut(v);
    }

    public synchronized V take() {
        if (isEmpty()) {
            throw new BufferEmptyException();
        }
        return doTake();
    }
}

class BufferFullException extends RuntimeException {
}

class BufferEmptyException extends RuntimeException {
}
