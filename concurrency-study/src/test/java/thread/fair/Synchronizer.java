package thread.fair;

public class Synchronizer{
    Lock lock = new Lock();
    public void doSynchronized() throws InterruptedException{
        this.lock.lock();
        try {
            //critical section, do a lot of work which takes a long time
        } finally {
            this.lock.unlock();
        }
    }
}