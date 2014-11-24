package thread.lock;

/**
 * Lock是不可重入的,outer()获得锁后调用inner()会阻塞
 */
public class Reentrant2 {
    private Lock lock = new Lock();
    public void outer() throws InterruptedException {
        lock.lock();
        inner();
        lock.unLock();
    }

    public void inner() throws InterruptedException {
        lock.lock();
        //do something
        lock.unLock();
    }
}
