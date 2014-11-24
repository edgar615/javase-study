package thread.busywait;

/**
 * 自旋锁
 */
public class MyWaitNotify3 {

    private MonitorObject myMonitorObject  = new MonitorObject();

    boolean wasSignalled = false;

    public void doWait() throws InterruptedException {
        synchronized (myMonitorObject) {
            while (!wasSignalled) {
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
