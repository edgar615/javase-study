package base;

import com.google.common.base.Stopwatch;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/2/4.
 */
public class StopwatchTest {

    @Test
    public void test() throws InterruptedException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        TimeUnit.SECONDS.sleep(1);
        Assert.assertTrue(stopwatch.isRunning());
        stopwatch.stop();
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));

        stopwatch = Stopwatch.createUnstarted();
        Assert.assertFalse(stopwatch.isRunning());
        stopwatch.start();
        TimeUnit.SECONDS.sleep(1);
        stopwatch.stop();
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }
}
