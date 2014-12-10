package concurreny.executorservice;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2014/11/26.
 */
public class InvokeAllExample2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Set<Callable<String>> callables = new HashSet<Callable<String>>();

        callables.add(new Callable<String>() {
            public String call() throws Exception {
                int rand =new Random().nextInt(2000);
                TimeUnit.MILLISECONDS.sleep(rand);
                return "Task 1";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                int rand =new Random().nextInt(2000);
                TimeUnit.MILLISECONDS.sleep(rand);
                return "Task 2";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                int rand =new Random().nextInt(1500);
                TimeUnit.MILLISECONDS.sleep(rand);
                return "Task 3";
            }
        });

        List<Future<String>> futures = executorService.invokeAll(callables, 1, TimeUnit.SECONDS);

        for (Future<String> future : futures) {
            try {
                System.out.println("result = " + future.get());
            } catch (CancellationException e) {
                System.out.println("timeout");
            }
        }

        executorService.shutdown();
    }
}
