package concurreny;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by edgar on 15-10-25.
 */
public class CompletableFutrueComposeExample {

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
