线程池

- int corePoolSize - 池中所保存的线程数，包括空闲线程。 
- int maximumPoolSize - 池中允许的最大线程数。 
- long keepAliveTime - 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间。 
- TimeUnit unit - keepAliveTime 参数的时间单位。 
- BlockingQueue<Runnable> workQueue - 执行前用于保持任务的队列。此队列仅保持由 execute 方法提交的 Runnable 任务。 
- ThreadFactory threadFactory - 执行程序创建新线程时使用的工厂。 
-  RejectedExecutionHandler handler - 由于超出线程范围和队列容量而使执行被阻塞时所使用的处理程序。 


方法
- getPoolSize() 返回线程池实际的线程数。
- getActiveCount() 返回在执行者中正在执行任务的线程数。
- getCompletedTaskCount() 返回执行者完成的任务数。
- getLargestPoolSize() 返回曾经同时位于池中的最大线程数。
- shutdownNow() 此方法立即关闭执行者。它不会执行待处理的任务，但是它会返回待处理任务的列表。当你调用这个方法时，正在运行的任务继续它们的执行，但这个方法并不会等待它们的结束。
- isTerminated() 如果你已经调用shutdown()或shutdownNow()方法，并且执行者完成关闭它的处理时，此方法返回true。
- isTerminating() 如果此执行程序处于在 shutdown 或 shutdownNow 之后正在终止但尚未完全终止的过程中，则返回 true。
- isShutdown() 如果你在执行者中调用shutdown()方法，此方法返回true。
- awaitTermination(long timeout, TimeUnit unit) 此方法阻塞调用线程，直到执行者的任务结束或超时。TimeUnit类是个枚举类，有如下常 量：DAYS，HOURS，MICROSECONDS， MILLISECONDS， MINUTES,，NANOSECONDS 和SECONDS。


### RejectedExecutionHandler 

<pre>
public class RejectedTaskController implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.printf("RejectedTaskController: The task %s has been rejected\n",r.toString());
        System.out.printf("RejectedTaskController: %s\n",executor.toString());
        System.out.printf("RejectedTaskController: Terminating:%s\n",executor.isTerminating());
        System.out.printf("RejectedTaksController: Terminated:%s\n",executor.isTerminated());
    }
}
</pre>

<pre>
public class Task implements Runnable {
    private String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("Task "+name+": Starting");
        try {
            long duration=(long)(Math.random()*10);
            System.out.printf("Task %s: ReportGenerator: Generating a report during %d seconds\n",name,duration);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Task %s: Ending\n",name);
    }
}
</pre>

<pre>
public static void main(String[] args) {
    ThreadPoolExecutor executorService= (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
    executorService.setRejectedExecutionHandler(new RejectedTaskController());
    for (int i = 0; i < 10; i ++) {
        executorService.execute(new Task(i+ ""));
    }
    //关闭
    executorService.shutdown();
    executorService.execute(new Task("11"));
}
</pre>


### ScheduledThreadPoolExecutor
ScheduledThreadPoolExecutor它可另行安排在给定的延迟后运行命令，或者定期执行命令。需要多个辅助线程时，或者要求 ThreadPoolExecutor 具有额外的灵活性或功能时，此类要优于 Timer。
 
<pre>
ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
Future<String> future = executorService.schedule(new Callable<String>() {
    @Override
    public String call() throws Exception {
        System.out.println("Executed!");
        return "Called!";
    }
}, 5, TimeUnit.SECONDS);
System.out.println(future.get());
executorService.shutdown();
</pre>

- schedule(Callable<V> callable, long delay, TimeUnit unit) 创建并执行在给定延迟后启用的 ScheduledFuture。
- schedule(Runnable command, long delay, TimeUnit unit) 创建并执行在给定延迟后启用的一次性操作。
- scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) 创建并执行一个在给定初始延迟后首次启用的定期操作，后续操作具有给定的周期；也就是将在 initialDelay 后开始执行，然后在 initialDelay+period 后执行，接着在 initialDelay + 2 * period 后执行，依此类推
- scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) 创建并执行一个在给定初始延迟后首次启用的定期操作，随后，在每一次执行终止和下一次执行开始之间都存在给定的延迟。

<pre>
ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
executorService.scheduleAtFixedRate(new Runnable() {

   @Override
   public void run() {
       System.out.println("Executed!" + new Date());
   }
}, 5, 3, TimeUnit.SECONDS);
</pre>