package concurrencyinpractice.chapter06;

import java.util.Date;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2014/12/10.
 */
public class ScheduledThreadPoolExample2 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Executed! " + new Date());
            }
        }, 5, 3, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(15);
        executorService.shutdown();
    }
}
