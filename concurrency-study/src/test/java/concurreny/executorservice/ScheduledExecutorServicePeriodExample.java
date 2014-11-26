package concurreny.executorservice;

import java.util.Date;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2014/11/26.
 */
public class ScheduledExecutorServicePeriodExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
       executorService.scheduleAtFixedRate(new Runnable() {

           @Override
           public void run() {
               System.out.println("Executed!" + new Date());
           }
       }, 5, 3, TimeUnit.SECONDS);
//        executorService.shutdown();
    }
}
