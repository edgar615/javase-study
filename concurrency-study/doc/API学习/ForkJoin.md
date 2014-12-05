http://www.infoq.com/cn/articles/fork-join-introduction

Fork/Join框架是Java7提供了的一个用于并行执行任务的框架， 是一个把大任务分割成若干个小任务，最终汇总每个小任务结果后得到大任务结果的框架。

Fork/Join使用两个类来完成以上两件事情：

    ForkJoinTask：我们要使用ForkJoin框架，必须首先创建一个ForkJoin任务。它提供在任务中执行fork()和join()操作的机制，通常情况下我们不需要直接继承ForkJoinTask类，而只需要继承它的子类，Fork/Join框架提供了以下两个子类：
        RecursiveAction：用于没有返回结果的任务。
        RecursiveTask ：用于有返回结果的任务。
    ForkJoinPool ：ForkJoinTask需要通过ForkJoinPool来执行，任务分割出的子任务会添加到当前工作线程所维护的双端队列中，进入队列的头部。当一个工作线程的队列里暂时没有任务时，它会随机从其他工作线程的队列的尾部获取一个任务。


<pre>
public class CountTask extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 2;

    private final int start;

    private final int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if ((end - start) <= THRESHOLD) {
            //执行最小任务
            for (int i = start; i <= end; i ++) {
                sum += i;
            }
            System.out.println("start : " + start + " end : " + end + " sum : " + sum);
        } else {
            //拆分
            int middle = (end + start) / 2;
            CountTask leftTask = new CountTask(start, middle);
            CountTask rightTask = new CountTask(middle + 1, end);
            leftTask.fork();
            rightTask.fork();

            int leftResult = leftTask.join();
            int rightResult = rightTask.join();
            sum = leftResult + rightResult;
        }
        return sum;
    }
}
</pre>

<pre>
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CountTask countTask = new CountTask(1, 10);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Future<Integer> future = forkJoinPool.submit(countTask);
        System.out.println(future.get());
    }
</pre>

### 异常处理

<pre>
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
</pre>

<pre>
CountTask2 countTask = new CountTask2(1, 12);
ForkJoinPool forkJoinPool = new ForkJoinPool();
Future<Integer> future = forkJoinPool.submit(countTask);
System.out.println(future.get());
if (countTask.isCompletedAbnormally()) {
    System.out.println(countTask.getException());
}
</pre>