package concurrent;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/6/23.
 */
public class FutureCallbackExample {
    private static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    public static void main(String[] args) {
        ListenableFuture<String> listenableFuture =
                service.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        TimeUnit.SECONDS.sleep(5);
                        return "123";
                    }
                });
        FutureCallbackImpl callback = new FutureCallbackImpl();
        Futures.addCallback(listenableFuture, callback);
//        Futures.addCallback(listenableFuture, callback, service);
        System.out.println(callback.getCallbackResult());
    }
}
