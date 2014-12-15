package concurrencyinpractice.chapter08;

import java.util.concurrent.ThreadFactory;

/**
 * Created by Administrator on 2014/12/15.
 */
public class MyThreadFactory implements ThreadFactory {

    private final String poolName;

    public MyThreadFactory(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new MyAppThread(r, poolName);
    }
}
