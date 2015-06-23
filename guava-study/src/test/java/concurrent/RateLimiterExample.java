package concurrent;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2015/6/23.
 */
public class RateLimiterExample {

    public static void main(String[] args) {
        RateLimiter limiter = RateLimiter.create(3.0);
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i ++) {
            limiter.acquire();
            service.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(new Date());
                }
            });
        }
    }
}
