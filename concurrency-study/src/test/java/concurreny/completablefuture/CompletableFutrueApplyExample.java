package concurreny.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by edgar on 15-10-25.
 */
public class CompletableFutrueApplyExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<String> completableFutureBigCompute = CompletableFuture.supplyAsync(() -> {
// big computation
            return "10";
        });

        //It is possible to join different CompletableFuture and use its results in future calculations using the methods thenApply() and thenCompose():
        CompletableFuture thenApply = completableFutureBigCompute.thenApply(CompletableFutrueApplyExample::continueWithSomethingElse);

        System.out.println("thenApply " + thenApply.get());
        System.out.println("thenApply " + thenApply.isDone());

        completableFutureBigCompute = CompletableFuture.supplyAsync(() -> {
// big computation
            return "12";
        });

        thenApply = completableFutureBigCompute.thenApply(CompletableFutrueApplyExample::continueWithSomethingElse);

        System.out.println("thenApply " + thenApply.get());
        System.out.println("thenApply " + thenApply.isDone());
    }

    private static CompletableFuture<Double> continueWithSomethingElse(String str) {
        if ("10".equals(str))
            return CompletableFuture.supplyAsync(() -> {
                System.out.println("str passed is 10");
                return 22.4;
            });
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("str passed is not 10");
            return 11.4;
        });
    }

}
