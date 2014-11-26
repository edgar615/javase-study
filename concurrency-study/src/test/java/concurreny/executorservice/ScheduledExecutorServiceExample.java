package concurreny.executorservice;

import java.util.concurrent.*;

/**
 * Created by Administrator on 2014/11/26.
 */
public class ScheduledExecutorServiceExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
        Future<String> future = executorService.schedule(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("Executed!");
                return "Called!";
            }
        }, 5, TimeUnit.SECONDS);
        System.out.println(future.get());
        executorService.shutdown();
    }
}
