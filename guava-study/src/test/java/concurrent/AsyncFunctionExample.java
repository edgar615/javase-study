package concurrent;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2015/6/23.
 */
public class AsyncFunctionExample implements AsyncFunction<Long, String> {

    private ConcurrentMap<Long, String> map = Maps.newConcurrentMap();
    private ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    @Override
    public ListenableFuture<String> apply(Long input) throws Exception {
        if (map.containsKey(input)) {
            SettableFuture<String> listenableFuture = SettableFuture.create();
            listenableFuture.set(map.get(input));
            return listenableFuture;
        } else {
            return service.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    String retrieved = Thread.currentThread().getName();//service.get(input)
                    map.putIfAbsent(input, retrieved);
                    return retrieved;
                }
            });
        }
    }

    public static void main(String[] arg) throws Exception {
        AsyncFunctionExample example = new AsyncFunctionExample();
        System.out.println(example.apply(1L).get());
    }
}
