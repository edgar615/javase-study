package concurreny.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by edgar on 15-10-25.
 */
public class CompletableFutrueComposeExample {

    //有时你想运行一些future的值（当它准备好了），但这个函数也返回了future。CompletableFuture足够灵活地明白我们的函数结果现在应该作为顶级的future，对比CompletableFuture<CompletableFuture>。方法 thenCompose()相当于Scala的flatMap：
    //thenCompose()是一个重要的方法允许构建健壮的和异步的管道，没有阻塞和等待的中间步骤。
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<String> completableFutureBigCompute = CompletableFuture.supplyAsync(() -> {
// big computation
            return "10";
        });

        //It is possible to join different CompletableFuture and use its results in future calculations using the methods thenApply() and thenCompose():
        CompletableFuture thenCompose = completableFutureBigCompute.thenCompose(CompletableFutrueComposeExample::continueWithVeryImportantThing);
        System.out.println("thenCompose " + thenCompose.get());

        completableFutureBigCompute = CompletableFuture.supplyAsync(() -> {
// big computation
            return "12";
        });

        thenCompose = completableFutureBigCompute.thenCompose(CompletableFutrueComposeExample::continueWithVeryImportantThing);
        System.out.println("thenCompose " + thenCompose.get());

    }

    private static CompletableFuture<Double> continueWithVeryImportantThing(String str) {
        if ("10".equals(str))
            return CompletableFuture.supplyAsync(() -> {
                System.out.println("str passed is 10 in a very important task");
                return 22.5;
            });
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("str passed is not 10 in a very important task");
            return 11.5;
        });
    }
}
