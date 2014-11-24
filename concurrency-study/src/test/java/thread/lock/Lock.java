package thread.lock;

/**
 * Created by Administrator on 2014/11/24.
 */
public class Lock {

    private boolean isLocked = false;

    public synchronized  void lock() throws InterruptedException {
        while (isLocked) {//自旋锁
            wait();
        }
        isLocked = true;
    }

    public synchronized void unLock() {
        isLocked = false;
        notifyAll();
    }
}
