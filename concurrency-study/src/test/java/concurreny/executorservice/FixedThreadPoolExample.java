package concurreny.executorservice;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2014/11/26.
 */
public class FixedThreadPoolExample {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i ++) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    try {
                        TimeUnit.SECONDS.sleep(10);
                        System.out.println("getActiveCount: " + threadPoolExecutor.getActiveCount());
                        System.out.println("getCompletedTaskCount: " + threadPoolExecutor.getCompletedTaskCount());
                        System.out.println("getCorePoolSize: " + threadPoolExecutor.getCorePoolSize());
                        System.out.println("getLargestPoolSize: " + threadPoolExecutor.getLargestPoolSize());
                        System.out.println("getMaximumPoolSize: " + threadPoolExecutor.getMaximumPoolSize());
                        System.out.println("getPoolSize: " + threadPoolExecutor.getPoolSize());
                        System.out.println("getTaskCount: " + threadPoolExecutor.getTaskCount());
                        System.out.println("getQueue: " + threadPoolExecutor.getQueue());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        threadPoolExecutor.shutdown();
    }
}
