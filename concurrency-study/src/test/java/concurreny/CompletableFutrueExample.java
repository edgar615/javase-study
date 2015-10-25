package concurreny;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * Created by edgar on 15-10-25.
 */
public class CompletableFutrueExample {

    @Test
    public void testGet() throws ExecutionException, InterruptedException {
        //        CompletableFuture cf = new CompletableFuture();
        //using factory methods where the sync or async mode is specified and its task or process is also passed
        //Will wait for ever until the CompletableFuture is completed or cancelled.
        CompletableFuture cf = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync");
            return 100;
        });
        System.out.println(cf.get());
    }

    @Test
    public void testGetNow() throws ExecutionException, InterruptedException {
        //If the result of the computation is not present yet, the fallback passed as parameter is returned.
        CompletableFuture cf = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("error");
        });
        System.out.println(cf.getNow(1));
    }

    @Test
    public void testGetTimeOut() throws ExecutionException, InterruptedException, TimeoutException {
        //If the result of the computation is not present yet, the fallback passed as parameter is returned.
        CompletableFuture cf = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });
        //It waits x time units, and afterwards tries to return the computed value if available, if not an java.util.concurrent.TimeoutException is thrown.
        System.out.println(cf.get(3, TimeUnit.SECONDS));
        Assert.fail();
    }

    @Test
    public void testComplete() throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture completableFutureToBeCompleted2 = CompletableFuture.supplyAsync(() -> {
            for (int i = 0; i < 10; i--) {
                System.out.println("i " + i);
            }
            return 10;
        });
        //If we try to get the result of the completable shown above we are not going to get it never. We should complete or cancel it somehow.

        CompletableFuture completor = CompletableFuture.supplyAsync(() -> {
            System.out.println( "completing the other" );
            completableFutureToBeCompleted2.complete(222);
            return 10;
        });
        System.out.println( completor.get() );
        System.out.println( completableFutureToBeCompleted2.get() );

        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void testSupplyAsync() throws ExecutionException, InterruptedException {
        CompletableFuture cf = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync");
            return 100;
        });
    }

    @Test
    public void testRunAsync() throws ExecutionException, InterruptedException {
        CompletableFuture cf = CompletableFuture.runAsync(() -> {
            System.out.println("runAsync");
        });
    }

    @Test
    public void testExecutor() throws ExecutionException, InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        CompletableFuture cf = CompletableFuture.runAsync(() -> {
            System.out.println("runAsync");
        }, exec);

        cf = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync");
            return 100;
        }, exec);
    }

    @Test
    public void testAccept() throws ExecutionException, InterruptedException {
        CompletableFuture cf = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync");
            return 100;
        });
        cf.thenAccept(s -> System.out.println(s.getClass()));
    }

    @Test
    public void testRun() throws ExecutionException, InterruptedException {
        CompletableFuture cf = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync");
            return 100;
        });
        cf.thenRun(() -> System.out.println("run"));
    }

    @Test
    public void testAllOf() throws ExecutionException, InterruptedException {
        CompletableFuture cf = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync");
            return 1;
        });
        CompletableFuture cf2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync");
            return 2;
        });
        CompletableFuture cf3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync");
            return 3;
        });
        CompletableFuture c = CompletableFuture.allOf(cf, cf2, cf3);
    }
}
