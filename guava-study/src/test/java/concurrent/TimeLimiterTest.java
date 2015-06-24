package concurrent;

import com.google.common.util.concurrent.SimpleTimeLimiter;
import com.google.common.util.concurrent.TimeLimiter;
import com.google.common.util.concurrent.UncheckedTimeoutException;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by edgar on 15-6-24.
 */
public class TimeLimiterTest {

    @Test
    public void testTimeOut() throws Exception {
        TimeLimiter limiter = new SimpleTimeLimiter();
        long begin = System.currentTimeMillis();
        String result = limiter.callWithTimeout(new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(5);
                return "Hello";
            }
        }, 1, TimeUnit.SECONDS, false);
        System.out.println(result);
        System.out.println(System.currentTimeMillis() - begin);
    }

    @Test
    public void testNewProxy() {
        TimeLimiter limiter = new SimpleTimeLimiter();
        MyService myService = new MyService();
        IService proxyService = limiter.newProxy(myService, IService.class, 1, TimeUnit.SECONDS);
        try {
            System.out.println(proxyService.doSomething());
        } catch (UncheckedTimeoutException e) {
            System.out.println("timeout");
        }
    }

    public interface IService {
        String doSomething();
    }

    public class MyService implements IService {
        public String doSomething() {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "myService";
        }
    }
}
