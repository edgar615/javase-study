package concurreny.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by edgar on 15-10-25.
 */
public class CompletableFutrueHandleExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture completableFutureException = CompletableFuture.supplyAsync(() -> {
            // big task
            throw new RuntimeException("error");
            // return 10/0 // produces an exception, division by zero
        });

        //This method is very useful in case we want to catch exceptions produced during the computation of the completable future and provide some basic recovering mechanism:
        CompletableFuture handleOkError = completableFutureException.handle((ok, ex) -> {
            if (ok != null) {
                // return the value if everything ok
                return ok;
            } else {
                // in case of an exception print the stack trace and return null
//                ex.printStackTrace();
                if (ex instanceof Throwable) {
                    Throwable t =  (Throwable) ex;
                    t.printStackTrace();
                }
                return null;
            }
        });
        System.out.println("ok or error ? " + handleOkError.get());
    }

}
