package thread.semaphore;

/**
 * Created by Administrator on 2014/11/24.
 * 简单的信号量
 */
public class Semaphore {

    private boolean signal = false;

    public synchronized void take() {
        this.signal = true;
        this.notifyAll();
    }

    public synchronized void release() throws InterruptedException {
        while (!signal) {
            wait();
        }
        this.signal = false;
    }
}
