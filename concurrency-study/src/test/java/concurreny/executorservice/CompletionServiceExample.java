package concurreny.executorservice;

import java.util.Random;
import java.util.concurrent.*;

public class CompletionServiceExample {
 
    public static class Task implements Callable<Integer> {
        private int i;
 
        Task(int i) {
            this.i = i;
        }
 
        @Override
        public Integer call() throws Exception {
            int rand = new Random().nextInt(5000);
            Thread.sleep(rand);
            System.out.println(Thread.currentThread().getName() + "   " + i + " sleep " + rand);
            return i;
        }
    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        CompletionService<Integer> completionServcie = new ExecutorCompletionService<Integer>(
                pool);
        try {
            for (int i = 0; i < 10; i++) {
                completionServcie.submit(new CompletionServiceExample.Task(i));
            }
            for (int i = 0; i < 10; i++) {
                // take 方法等待下一个结果并返回 Future 对象。
                // poll 不等待，有结果就返回一个 Future 对象，否则返回 null。
                System.out.println(completionServcie.take().get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }
}