package concurreny.executorservice;

import java.util.concurrent.*;

/**
 * Created by Administrator on 2014/11/26.
 */
public class SubmitCallableExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("Asynchronous task");
                return "Callable Result";
            }
        });
        System.out.println(future.get());
        executorService.shutdown();
    }
}
