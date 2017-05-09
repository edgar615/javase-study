package concurrent;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.*;

/**
 * Created by Administrator on 2015/6/23.
 */
public class ListenableFutureExample {
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
        listenableFuture.addListener(new Runnable() {
            @Override
            public void run() {
                System.out.println("completion");
                try {
                    System.out.println(listenableFuture.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }, service);
      System.out.println("nobolck");
    }
}
