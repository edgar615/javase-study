package concurreny.completablefuture;

import com.sun.xml.internal.ws.util.CompletedFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * http://blog.zhouhaocheng.cn/posts/41
 * https://dzone.com/articles/java-8-definitive-guide
 * http://www.ibm.com/developerworks/library/j-jvmc2/index.html
 * http://blog.krecan.net/2013/12/25/completablefutures-why-to-use-async-methods/
 * http://www.javacodegeeks.com/2014/12/asynchronous-timeouts-with-completablefuture.html
 * http://www.javacodegeeks.com/2015/08/using-java-8-completablefuture-and-rx-java-observable.html
 * http://www.javacodegeeks.com/2013/05/java-8-completablefuture-in-action.html
 * http://www.javacodegeeks.com/2013/05/java-8-definitive-guide-to-completablefuture.html
 * http://examples.javacodegeeks.com/core-java/util/concurrent/java-8-concurrency-tutorial/
 * http://www.javacodegeeks.com/2014/05/java-8-features-tutorial.html
 *
 * http://winterbe.com/posts/2014/03/16/java-8-tutorial/
 */
public class CompletableFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture cf = CompletableFuture.supplyAsync(() -> {
           return 100;
        });
        //Will wait for ever until the CompletableFuture is completed or cancelled.
        System.out.println( "get  " + cf.get() );

        cf = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("error");
        });

        //If the result of the computation is not present yet, the fallback passed as parameter is returned.
        System.out.println("get  " + cf.getNow("hello"));
    }
}
