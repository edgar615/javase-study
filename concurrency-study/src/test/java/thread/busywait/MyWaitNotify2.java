package thread.busywait;

/**
 * notify()和notifyAll()方法不会保存调用它们的方法，因为当这两个方法被调用时，有可能没有线程处于等待状态。通知信号过后便丢弃了。因此，如果一个线程先于被通知线程调用wait()前调用了notify()，等待的线程将错过这个信号。这可能是也可能不是个问题。不过，在某些情况下，这可能使等待线程永远在等待，不再醒来，因为线程错过了唤醒信号。
 */
public class MyWaitNotify2 {

    private MonitorObject myMonitorObject  = new MonitorObject();

    boolean wasSignalled = false;

    public void doWait() throws InterruptedException {
        synchronized (myMonitorObject) {
            if (!wasSignalled) {
                myMonitorObject.wait();
            }
            wasSignalled = false;
        }
        //do something
    }

    public void doNotify() {
        synchronized (myMonitorObject) {
            wasSignalled = true;
            myMonitorObject.notifyAll();
        }
    }


}
