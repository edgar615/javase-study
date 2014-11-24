package thread.lock;

/**
 * Created by Administrator on 2014/11/24.
 */
public class ReentrantLock {
    private boolean isLocked = false;
    private Thread lockedBy = null;
    private int lockedCount = 0;

    public synchronized void lock() throws InterruptedException {
        Thread callingThread = Thread.currentThread();
        while (isLocked && lockedBy != callingThread) {
            wait();
        }
        isLocked = true;
        lockedCount ++;
        lockedBy = callingThread;
    }

    public synchronized void unLock() {
        Thread callingThread = Thread.currentThread();
        if (callingThread == Thread.currentThread()) {
            lockedCount --;
            if (lockedCount == 0) {
                lockedBy = null;
                isLocked = false;
                notifyAll();
            }
        }
    }
}
