package concurreny.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by edgar on 15-10-25.
 */
public class CompletableFutrueWaitEitherExample {

    //另一个有趣的事是CompletableFutureAPI可以等待第一个（与所有相反）完成的future。当你有两个相同类型任务的结果时就显得非常方便，你只要关心响应时间就行了，没有哪个任务是优先的。API方法(…Async变量也是可用的）
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
            return "20";
        });

        long start = System.currentTimeMillis();
//        cf1.thenAcceptBoth(cf2,  (s1, s2) -> {
//            System.out.println(s1);
//            System.out.println(s2);
//            System.out.println("end:" + System.currentTimeMillis());
//        });
        System.out.println(cf1.acceptEither(cf2,  (s) -> {
            System.out.println(s);
        }).get());
        System.out.println("end:" + (System.currentTimeMillis() - start) / 1000);

//        cf1.thenAcceptBoth(cf2, (s1, s2) -> {
//            System.out.println(s1);
//            System.out.println(s2);
//        }).thenRun(() -> System.out.println("end:" + (System.currentTimeMillis() - start) / 1000));
//        System.out.println("end:" + (System.currentTimeMillis() - start) / 1000);
    }


}
