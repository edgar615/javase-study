package concurreny.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by edgar on 15-10-25.
 */
public class CompletableFutrueExceptionallyExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture completableFutureException = CompletableFuture.supplyAsync(() -> {
            // big task
            return 10 / 2;
            // return 10/0 // produces an exception, division by zero
        });

        //This method is very useful in case we want to catch exceptions produced during the computation of the completable future and provide some basic recovering mechanism:
        CompletableFuture fallback = completableFutureException.exceptionally(x -> 0);
        System.out.println(fallback.get());//5

        completableFutureException = CompletableFuture.supplyAsync(() -> {
            // big task
            throw new RuntimeException("error");
//            return 10 / 2;
            // return 10/0 // produces an exception, division by zero
        });

        //exceptionally This method is very useful in case we want to catch exceptions produced during the computation of the completable future and provide some basic recovering mechanism:
        fallback = completableFutureException.exceptionally(x -> 0);
        System.out.println(fallback.get());//0
    }

}
