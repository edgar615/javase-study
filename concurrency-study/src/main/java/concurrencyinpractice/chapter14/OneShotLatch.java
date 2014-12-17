package concurrencyinpractice.chapter14;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Created by Administrator on 2014/12/17.
 */
public class OneShotLatch {
    private final Sync sync = new Sync();

    public void singal() {
        sync.releaseShared(0);
    }

    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(0);
    }

    private class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected int tryAcquireShared(int ignored) {
            return (getState() == 1) ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            setState(1);
            return true;
        }
    }
}
