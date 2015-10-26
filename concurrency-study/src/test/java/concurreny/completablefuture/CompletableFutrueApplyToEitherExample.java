package concurreny.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by edgar on 15-10-25.
 */
public class CompletableFutrueApplyToEitherExample {

    //applyToEither()算是 acceptEither()的前辈了。当两个futures快要完成时，后者只是简单地调用一些代码片段，applyToEither()将会返回一个新的future。
    // 当这两个最初的futures完成时，新的future也会完成。API有点类似于(…Async 变量也是可用的)
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
       //这个额外的fn功能在第一个future被调用时能完成。我不确定这个专业化方法的目的是什么，毕竟一个人可以简单地使用：fast.applyToEither(predictable).thenApply(fn)。因为我们坚持用这个API，但我们的确不需要额外功能的应用程序，我会简单地使用Function.identity()占位符：
//        CompletableFuture<String> fast = fetchFast();
//        CompletableFuture<String> predictable = fetchPredictably();
//        CompletableFuture<String> firstDone =
//                fast.applyToEither(predictable, Function.<String>identity());
        //第一个完成的future可以通过运行。请注意，从客户的角度来看，两个futures实际上是在firstDone的后面而隐藏的。客户端只是等待着future来完成并且通过applyToEither()使得当最先的两个任务完成时通知客户端。
    }


}
