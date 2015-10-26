package concurreny;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by edgar on 15-10-25.
 */
public class CompletableFutrueWaitAllExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
// big computation
            long start = System.currentTimeMillis();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf1 end:" + (System.currentTimeMillis() - start) / 1000);
            return "10";
        });
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
// big computation
            long start = System.currentTimeMillis();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf2 end:" + (System.currentTimeMillis() - start) / 1000);
            return "10";
        });

        long start = System.currentTimeMillis();
//        cf1.thenAcceptBoth(cf2,  (s1, s2) -> {
//            System.out.println(s1);
//            System.out.println(s2);
//            System.out.println("end:" + System.currentTimeMillis());
//        });
        System.out.println(cf1.thenAcceptBoth(cf2,  (s1, s2) -> {
            System.out.println(s1);
            System.out.println(s2);
        }).get());
        System.out.println("end:" + (System.currentTimeMillis() - start) / 1000);

//        cf1.thenAcceptBoth(cf2, (s1, s2) -> {
//            System.out.println(s1);
//            System.out.println(s2);
//        }).thenRun(() -> System.out.println("end:" + (System.currentTimeMillis() - start) / 1000));
//        System.out.println("end:" + (System.currentTimeMillis() - start) / 1000);
    }


}
