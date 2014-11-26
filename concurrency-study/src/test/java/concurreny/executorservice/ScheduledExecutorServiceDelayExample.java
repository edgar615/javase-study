package concurreny.executorservice;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2014/11/26.
 */
public class ScheduledExecutorServiceDelayExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
       executorService.scheduleWithFixedDelay(new Runnable() {

           @Override
           public void run() {
               System.out.println("Executed!" + new Date());
           }
       }, 5, 3, TimeUnit.SECONDS);
//        executorService.shutdown();
    }
}
