package concurreny;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by edgar on 15-10-25.
 */
public class CompletableFutrueCombineExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<String> completableFutureBigCompute = CompletableFuture.supplyAsync(() -> {
// big computation
            return "10";
        });

        // thenCombine offers the possibility to combine two completables that are totally
        // independent
        String login = "dani", password = "pass", land = "spain";
        CompletableFuture<Boolean> loginCompletable = checkLogin(login, password);
        CompletableFuture<Boolean> checkLandCompletable = checkLand(land);
        CompletableFuture<String> welcomeOrNot = loginCompletable.thenCombine(checkLandCompletable,
                (cust, shop) -> welcome(cust, shop));

        System.out.println(welcomeOrNot.get());
    }

    private static String welcome(Boolean login, Boolean land) {

        // checks both and returns
        if (login && land)
            return "welcome";
        else
            return "not welcome";

    }

    private static CompletableFuture<Boolean> checkLand(String land) {
        // only Spanish are allowed
        return CompletableFuture.supplyAsync(() -> {
            // big task with back end dependencies
            return "spain".equals(land);
        });
    }

    private static CompletableFuture<Boolean> checkLogin(String login, String password) {
        return CompletableFuture.supplyAsync(() -> {
            // very hard authentication process
            return login != null && password != null;
        });
    }

}
