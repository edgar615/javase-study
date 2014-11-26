package concurreny.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * Created by Administrator on 2014/11/26.
 */
public class CountTask2 extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 2;

    private final int start;

    private final int end;

    public CountTask2(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if ((end - start) <= THRESHOLD) {
            //执行最小任务
            for (int i = start; i <= end; i ++) {
                if (i == 11) {
                    throw new IllegalArgumentException( "i = " + i);
                }
                sum += i;
            }
            System.out.println("start : " + start + " end : " + end + " sum : " + sum);
        } else {
            //拆分
            int middle = (end + start) / 2;
            CountTask2 leftTask = new CountTask2(start, middle);
            CountTask2 rightTask = new CountTask2(middle + 1, end);
            leftTask.fork();
            rightTask.fork();

            int leftResult = leftTask.join();
            int rightResult = rightTask.join();
            sum = leftResult + rightResult;
        }
        return sum;
    }
}
