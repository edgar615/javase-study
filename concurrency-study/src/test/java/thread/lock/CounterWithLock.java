package thread.lock;

public class CounterWithLock {

    private int count = 0;

    private Lock lock = new Lock();

    public int inc() throws InterruptedException {
        lock.lock();
        int newCount = ++ count;
        lock.unLock();
        return newCount;
    }

}