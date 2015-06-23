package concurrent;

import com.google.common.util.concurrent.*;

import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2015/6/23.
 */
public class FutureFallbackExample {
    private static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ListenableFuture future = service.submit(new Runnable() {
            @Override
            public void run() {
                throw new IllegalArgumentException("test");
            }
        });
        FutureFallbackImpl futureFallback = new FutureFallbackImpl();
        ListenableFuture<String> newFuture = Futures.withFallback(future, futureFallback);
        System.out.println(newFuture.get());

        future = service.submit(new Runnable() {
            @Override
            public void run() {
            }
        });
        futureFallback = new FutureFallbackImpl();
        newFuture = Futures.withFallback(future, futureFallback);
        System.out.println(newFuture.get());
    }
}
