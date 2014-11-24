package thread.busywait;

/**
 * 忙等待没有对运行等待线程的CPU进行有效的利用，除非平均等待时间非常短。否则，让等待线程进入睡眠或者非运行状态更为明智，直到它接收到它等待的信号。
 */
public class BusyWaitRunnable implements Runnable {
    private MySignal signal;

    public BusyWaitRunnable(MySignal signal) {
        this.signal = signal;
    }

    @Override
    public void run() {
        while (signal.hasDataToProcess()) {
            //do something
        }
    }
}
