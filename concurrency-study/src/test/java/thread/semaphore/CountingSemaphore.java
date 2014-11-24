package thread.semaphore;

/**
 * Created by Administrator on 2014/11/24.
 */
public class CountingSemaphore {
    private int signals = 0;

    public synchronized void take() {

        this.signals++;

        this.notify();

    }

    public synchronized void release() throws InterruptedException{

        while(this.signals == 0) wait();

        this.signals--;

    }
}
