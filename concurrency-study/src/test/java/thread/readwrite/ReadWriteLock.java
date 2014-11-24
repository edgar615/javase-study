package thread.readwrite;

/**
 * Created by Administrator on 2014/11/24.
 */
public class ReadWriteLock {
    private int readers = 0;
    private int writers = 0;
    private int writeRequests = 0;

    public synchronized void lockRead()
            throws InterruptedException{
        while (writers > 0 || writeRequests > 0) {
            wait();
        }
        readers ++;
    }

    public synchronized void unLockRead() {
        readers --;
        notifyAll();
    }

    public synchronized void lockWrite() throws InterruptedException {
        writeRequests ++;
        while (readers > 0 || writers > 0) {
            wait();
        }
        writers ++;
        writeRequests --;
    }

    public synchronized void unLockWrite() {
        writers --;
        notifyAll();
    }
}
