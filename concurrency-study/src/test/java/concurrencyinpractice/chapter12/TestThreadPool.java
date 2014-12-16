package concurrencyinpractice.chapter12;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestThreadPool {

    private final TestingThreadFactory threadFactory = new TestingThreadFactory();

    @Test
    public void testPoolExpansion() throws InterruptedException {
        int MAX_SIZE = 10;
        ExecutorService exec = Executors.newFixedThreadPool(MAX_SIZE, threadFactory);

        for (int i = 0; i < 10 * MAX_SIZE; i++)
            exec.execute(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(Long.MAX_VALUE);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        for (int i = 0;
             i < 20 && threadFactory.numCreated.get() < MAX_SIZE;
             i++)
            Thread.sleep(100);
        Assert.assertEquals(threadFactory.numCreated.get(), MAX_SIZE);
        exec.shutdownNow();
    }
}

