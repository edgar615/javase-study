package concurrent;

import com.google.common.util.concurrent.FutureFallback;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

import java.io.FileNotFoundException;

/**
 * Created by Administrator on 2015/6/23.
 */
public class FutureFallbackImpl implements FutureFallback<String> {
    @Override
    public ListenableFuture<String> create(Throwable t) throws Exception {
        if (t instanceof IllegalArgumentException) {
            System.out.println("d");
            SettableFuture<String> settableFuture = SettableFuture.create();
            settableFuture.set("Not Found");
            return  settableFuture;
        }
        throw new Exception(t);
    }
}
