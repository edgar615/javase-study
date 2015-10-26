package concurreny;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by edgar on 15-10-25.
 */
public class CompletableFutrueCompleteExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
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
        System.out.println(completableFutureToBeCompleted2.get());
    }
}
