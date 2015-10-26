package concurreny.completablefuture;

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
        //无参方法Executor是以…Async结尾同时将会使用ForkJoinPool.commonPool()(全局的，在JDK8中介绍的通用池），这适用于CompletableFuture类中的大多数的方法。runAsync()易于理解，注意它需要Runnable，因此它返回CompletableFuture<Void>作为Runnable不返回任何值。如果你需要处理异步操作并返回结果，使用Supplier<U>:
        CompletableFuture cf = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("supplyAsync");
            return 100;
        });
    }

    @Test
    public void testRunAsync() throws ExecutionException, InterruptedException {
        //无参方法Executor是以…Async结尾同时将会使用ForkJoinPool.commonPool()(全局的，在JDK8中介绍的通用池），这适用于CompletableFuture类中的大多数的方法。runAsync()易于理解，注意它需要Runnable，因此它返回CompletableFuture<Void>作为Runnable不返回任何值。如果你需要处理异步操作并返回结果，使用Supplier<U>:
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
        //在future的管道里有两种典型的“最终”阶段方法。他们在你使用future的值的时候做好准备，当 thenAccept()提供最终的值时，thenRun执行 Runnable，这甚至没有方法去计算值
        cf.thenAccept(s -> System.out.println(s.getClass()));
    }

    @Test
    public void testRun() throws ExecutionException, InterruptedException {
        CompletableFuture cf = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync");
            return 100;
        });
        //在future的管道里有两种典型的“最终”阶段方法。他们在你使用future的值的时候做好准备，当 thenAccept()提供最终的值时，thenRun执行 Runnable，这甚至没有方法去计算值
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
        //allOf()当所有的潜在futures完成时，使用了一个futures数组并且返回一个future（等待所有的障碍）。另一方面anyOf()将会等待最快的潜在futures，请看一下返回futures的一般类型
        CompletableFuture c = CompletableFuture.allOf(cf, cf2, cf3).thenAccept((s) -> {
            try {
                System.out.println(cf.get());
                System.out.println(cf2.get());
                System.out.println(cf3.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
    }
}
