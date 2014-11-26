package concurreny.executorservice;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2014/11/26.
 */
public class SubmitRunnableExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Asynchronous task");
            }
        });
        System.out.println(future.get());
        executorService.shutdown();
    }
}
