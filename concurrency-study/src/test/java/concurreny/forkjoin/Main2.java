package concurreny.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2014/11/26.
 */
public class Main2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CountTask2 countTask = new CountTask2(1, 12);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Future<Integer> future = forkJoinPool.submit(countTask);
        System.out.println(future.get());
        if (countTask.isCompletedAbnormally()) {
            System.out.println(countTask.getException());
        }
    }
}
