package concurrencyinpractice.chapter06;

import java.util.concurrent.*;

/**
 * Created by Administrator on 2014/12/10.
 */
public class ScheduledThreadPoolExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
        Future<String> future =executorService.schedule(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("Executed1!");
                return "Called1!";
            }
        }, 5, TimeUnit.SECONDS);
        Future<String> future2 =executorService.schedule(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("Executed2!");
                return "Called2!";
            }
        }, 3, TimeUnit.SECONDS);
        System.out.println(future.get());
        System.out.println(future2.get());
        executorService.shutdown();
    }
}
