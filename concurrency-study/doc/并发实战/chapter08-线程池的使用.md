只要条件允许，Executor可以自由地重用这些线程。在标准的Executor实现中，当知心需求较低时将回收空闲线程，而当需求增加时将添加新的线程，并且如果从任务中抛出了一个未检查异常，那么将用一个新的工作线程来代替抛出异常的线程。

只有当线程本地值的生命周期受限于任务的生命周期时，在线程池的线程中使用ThreadLocal才有意义，而在线程池的线程中不应该使用ThreadLocal在任务之间传递值。

只有当任务都是同类型的并且相互独立时，线程池的性能才能达到最佳。如果将运行时间较长的运行任务与运行时间较短的任务混合在一起，那么除非线程池很大，否则将可能造成“阻塞”。
如果提交的任务依赖于其他任务，那么除非线程池无限大，否则将可能造成死锁。

#### 线程饥饿死锁
在线程池中，如果任务依赖于其他任务，那么可能产生死锁，在单线程的Executor中，如果一个任务将另一个任务提交到同一个Executor，并且等待这个被提交任务的结果，那么通常会引发死锁。
第二个任务停留在工作队列中，并等待第一个任务完成，而第一个任务又无法完成，因为它在等待第二个任务的完成。
在更大的线程池中，如果所有正在执行任务的线程都由于等待其他仍处于工作队列中的任务而阻塞，那么会发生同样的问题。
这种现象被称为线程饥饿死锁，只要线程池中的任务需要无限期地等待一些必须由池中其他任务才能提供的资源或条件，例如某个任务等待另一个任务的返回值或执行结果，那么除非线程池足够大，否则将发生线程饥饿死锁。

*在单线程的Executor中任务发生死锁*

<pre>
public class ThreadDeadlock {
    ExecutorService exec = Executors.newSingleThreadExecutor();

    public class LoadFileTask implements Callable<String> {
        private final String fileName;

        public LoadFileTask(String fileName) {
            this.fileName = fileName;
        }

        public String call() throws Exception {
            // Here's where we would actually read the file
            return "";
        }
    }

    public class RenderPageTask implements Callable<String> {
        public String call() throws Exception {
            Future<String> header, footer;
            header = exec.submit(new LoadFileTask("header.html"));
            footer = exec.submit(new LoadFileTask("footer.html"));
            String page = renderBody();
            // Will deadlock -- task waiting for result of subtask
            return header.get() + page + footer.get();
        }

        private String renderBody() {
            // Here's where we would actually render the page
            return "";
        }
    }
}
</pre>

每当提交了一个由依赖性的Executor任务时，要清楚地知道可能会出现线程“饥饿”死锁，因此需要在代码或配置Executor的配置文件中记录线程池的大小线程或配置限制。

除了在线程池大小上的显式限制外，还可能由于其他资源上的约束而存在一些隐式限制。
如果应用程序使用一个包含10个连接的JDBC连接池，并且每个任务需要一个数据库连接，那么线程池就好像只有10个线程，因为当超过10个任务时，新的任务需要等待其他任务释放连接。

#### 运行时间较长的任务

缓解执行时间较长任务造成的影响的技术：限定任务等待资源的时间，而不要无限制地等待。
在平台类库的大多数可阻塞方法中，都同时定义了限时版本和无限时版本。
如果等待超时，可以把任务标识为失败，然后终止任务或者将任务重新放回队列以便随后执行。

如果线程池中总是充满被阻塞的任务，那么也可能表明线程池的规模过小。

### 设置线程池的大小
线程池的理想大小取决于被提交任务的类型以及所部署系统的特性。在代码中通常不会固定线程池的大小，而应该通过某种配置机制来提供，或者根据Runtime.availableProcessors来动态计算。

为了正确的定制线程池的长度，你需要理解你的计算环境、资源预算和任务的自身特性。部署系统中安装了多少个CPU？多少内存？任务主要执行的是计算、I/O还是一些混合操作？它们是否需要像JDBC Connection这样的稀缺资源？如果你有不同类别的任务，它们拥有差别很大的行为，那么应该考虑使用多个不同的线程池，这样每个线程池可以根据不同任务的工作负载进行调节。

对于计算密集型的任务，一个有Ncpu 个处理器的系统通常通过使用一个Ncpu +1个线程的线程池来获得最优的利用率（计算密集型的线程恰好在某时因为发生一个页错误或者因为其他原因而暂停，刚好有一个“额外”的线程，可以确保在这样的情况下CPU周期不会中断工作）。对于包含了I/O和其他阻塞操作的任务，不是所有的线程都会在所有的时间被调度，因此你需要一个更大的池。为了正确地设置线程池的长度，你必须估算出任务花在等待的时间与用来计算的时间的比率；这个估算值不必十分精确，而且可以通过一些监控工具获得。你还可以选择另一种方法来调节线程池的大小，在一个基准负载下，使用不同大小的线程池运行你的应用程序，并观察CPU利用率的水平。

 给定下列定义：

    Ncpu = CPU的数量
    Ucpu = 目标CPU的使用率，0 ≤ Ucpu≤ 1
    W/C = 等待时间与计算时间的比率

为保持处理器达到期望的使用率，最优的池的大小等于：

Nthreads = Ncpu * Ucpu * ( 1 + W/C )

可以使用Runtime来获得CPU的数目：

int N_CPUS = Runtime.getRuntime().availableProcessors();

CPU周期并不是唯一可以使用线程池管理的资源。其他可以约束资源池大小的资源包括：内存、文件句柄、套接字句柄和数据库连接等。计算这些资源池的大小约束非常简单：首先累加出每一个任务需要的这些资源的总量，然后除以可用的总量。所得的结果是池大小的上限。

### 配置ThreadPoolExecutor

线程池属性

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

#### 线程的创建与销毁
线程池的基本大小，最大大小，以及存活时间等因素共同负责线程的创建于销毁。
基本大小也就是线程池的目标大小，即在没有任务执行时线程池的大小，并且只有在工作队列满了的情况下才会创建超出这个数量的线程。
线程池的最大大小表示可以同时活动的线程数量的上限。
如果某个线程的空闲时间超过了存活时间，那么将被标记为可回收的，并且当线程池的当前大小超过了基本大小时，这个线程将被终止。

如果新请求的到达速率超过了线程池的处理速率，那么新到来的请求将累积起来。在线程池中，这些请求会在一个由Executor管理的Runnable队列中等待，而不会像相处那样去竞争CPU资源。
通过一个Runnable和一个链表节点来表现一个等待中的任务，当然比使用线程来表示的开销低很多，但如果客户提交给服务器请求的速率超过了服务器的处理速率，那么仍可能会耗尽资源。

即使请求的平均达到速率很稳定，也仍然会出现请求突增的情况，尽管队列有助于缓解任务的突增问题，但如果任务持续高速地到来，那么最终还是会抑制请求的达到率已避免耗尽内存。
甚至在耗尽内存之前，响应性能也随着任务队列的增长而变得越来越糟。

ThreadPoolExecutor允许提供一个BlockingQueue来保存等待执行的任务。基本的任务排队方式有3种：无界队列、有界队列和同步移交。队列的选择与其他的配置参数有关。

newFixedThreadPool和newSingleThreadExecutor在默认情况下将使用一个无界的LinkedBlockingQueue。如果所有工作者线程都处于忙碌状态，那么任务将在队列中等候。
如果队列持续快速地到达，并且超过了线程池处理它们的速度，那么队列将无限制地增加。

一种更稳妥的资源管理策略是使用有界队列，例如ArrayBlockingQueue，有界的LinkedBlockingQueue、ProiorityBlockingQueue。
有界队列有助于避免资源耗尽的情况发生，但它又带来了新的问题：当队列填满后，新的任务该怎么办？

在使用有界队列时，队列的大小与线程池的大小必须一起调节。如果线程池较小而队列较大，那么有助于减少内存使用量，降低CPU的使用率，同时还可以减少上下文切换，但付出的代价是可能会限制吞吐量。

对于非常大的或者无界的线程池，可以通过SynchronousQueue来避免任务排队，以及直接将任务从生产者移交给工作者线程。
只有当线程池是无界的或者可以拒绝任务时，SynchronousQueue才有实际价值。

当使用像LinkedBlockingQueue或者ArrayBlockingQueue这样的FIFO队列时，任务的执行顺序与它们的到达顺序相同。
如果想进一步控制任务执行顺序，还可以使用PriorityBlockingQueue，这个队列将根据优先级来安排任务。任务的优先级是通过自然顺序或者Comparator来定义的。

对于Executor，newCachedThreadPool工厂方法是一种很好的默认选择，它能提供比固定大小的线程池更好的排队性能。当需要限制当前任务的数量以满足资源管理需求时，那么可以选择固定大小的线程池，就像在接受网络客户请求的服务器应用程序中，如果不进行限制，那么很容易发生过载问题。

#### 饱和策略
当有界策略被填满后，饱和策略开始发挥作用。ThreadPoolExecutor的饱和策略可以通过调用setRejectedExecutionHandler来修改。（如果某个任务被提交到了一个已被关闭的Executor时，也会用到饱和策略。）
JDK提供了几种不同的RejectedExecutionHandler实现，每种实现都包含有不同的饱和策略：AbortPolicy、CallerRunsPolicy、DiscardPolicy和DiscardOldestPolicy。

AbortPolicy（中止策略）是默认的饱和策略，该策略将抛出未检查的RejectedExecutionException。
当心提交的任务无法保存到队列中等待执行时，DiscardPolicy（抛弃策略）抛弃策略会悄悄抛弃该任务。DiscardOldestPolicy（抛弃最旧的策略）会抛弃下一个将被执行的任务，任何尝试重新提交新的任务。
（如果工作队列是一个优先队列，那么“抛弃最旧的”策略将导致抛弃优先级最高的任务）

CallerRunsPolicy（调用者运行策略）实现了一种调节机制，该策略既不会抛弃任务，也不会抛出异常，而是将某些任务回退到策略者，从而降低新任务的流量。
它不会再线程池的某个线程中执行新提交的任务，而是在一个调用了execute的线程中执行该任务。

当工作队列被填满后，没有预定义的饱和策略来阻塞execute。任何通过使用Semaphore来限制任务的达到率，就可以实现这个功能。

<pre>
public class BoundedExecutor {
    private final Executor exec;
    private final Semaphore semaphore;

    public BoundedExecutor(Executor exec, Semaphore semaphore) {
        this.exec = exec;
        this.semaphore = semaphore;
    }

    public void submitTask(final Runnable command) throws InterruptedException {
        semaphore.acquire();
        try {
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        command.run();
                    } finally {
                        semaphore.release();
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            semaphore.release();
        }
    }
}
</pre>

#### 线程工厂
每当线程池需要创建一个线程时，都是通过线程工厂方法。默认的线程工程方法将创建一个新的、非守护的线程，并且不包含特殊的配置信息。

*自定义的线程工厂*

<pre>
public class MyThreadFactory implements ThreadFactory {

    private final String poolName;

    public MyThreadFactory(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new MyAppThread(r, poolName);
    }
}
</pre>

<pre>
public class MyAppThread extends Thread {
    public static final String DEFAULT_NAME = "MyAppThread";
    private static volatile boolean debugLifecycle = false;
    private static final AtomicInteger created = new AtomicInteger();
    private static final AtomicInteger alive = new AtomicInteger();
    private static final Logger log = Logger.getAnonymousLogger();

    public MyAppThread(Runnable r) {
        this(r, DEFAULT_NAME);
    }

    public MyAppThread(Runnable runnable, String name) {
        super(runnable, name + "-" + created.incrementAndGet());
        setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread t, Throwable e) {
                log.log(Level.SEVERE,
                        "UNCAUGHT in thread " + t.getName(), e);
            }
        });
    }

    public void run() {
        // Copy debug flag to ensure consistent value throughout.
        boolean debug = debugLifecycle;
        if (debug) log.log(Level.FINE, "Created " + getName());
        try {
            alive.incrementAndGet();
            super.run();
        } finally {
            alive.decrementAndGet();
            if (debug) log.log(Level.FINE, "Exiting " + getName());
        }
    }

    public static int getThreadsCreated() {
        return created.get();
    }

    public static int getThreadsAlive() {
        return alive.get();
    }

    public static boolean getDebug() {
        return debugLifecycle;
    }

    public static void setDebug(boolean b) {
        debugLifecycle = b;
    }
}
</pre>

如果在应用程序中需要利用安全策略来控制对某些特殊代码库的访问权限，那么可以通过Executor中的privilegedThreadFactory工厂来定制自己的线程工厂。
通过这种方式创建处理的线程，将于创建privilegedThreadFactory的线程拥有相同的访问权限、AccessControlContext和contextClassLoader。

#### 在调用构造函数之后再定制ThreadPoolExecutor
在调用完ThreadPoolExecutor构造函数之后，仍然可以通过设置函数（Setter）来修改大多数传递给它的构造函数的参数。

在Executors中包含一个unconfigurableExecutorService工厂方法，该方法对一个现有的ExecutorService进行包装，使其只暴露出ExecutorService的方法，因此不能对它进行配置。
newSingleThreadExecutor返回按这种方式封装的ExecutorService，而不是最初的ThreadPoolExecutor。

### 扩展ThreadPoolExecutor
ThreadPoolExecutor是可以扩展的，它提供了几个可以在子类化中改写的方法：beforeExecute、afterExecute和terminated，这些方法可以用于扩展ThreadPoolExecutor的性格。

在执行任务的线程中调用beforeExecute和afterExecute等方法，在这些方法中还可以添加日志、计时、监视或统计信息收集的功能。无论任务是从run中正常返回，还是抛出一个异常而返回，afterExecute都会被调用。
（如果任务在完成后带有一个Error，那么就不会调用afterExecute）如果beforeExecute抛出一个RuntimeException，那么任务将不再被执行，并且afterExecute也不会被调用。

在线程池完成关闭操作时调用terminated，也就是在所有任务都已经完成并且所有工作者线程也已经关闭后，terminated可以用来释放Executor在生命周期里分配的各种资源，此外还可以执行发送通知、记录日志或者收集finalize统计信息等操作。

<pre>
public class TimingThreadPool extends ThreadPoolExecutor {

    private final ThreadLocal<Long> startTime = new ThreadLocal<>();
    private final Logger log = Logger.getLogger("TimingThreadPool");
    private final AtomicLong numTasks = new AtomicLong();
    private final AtomicLong totalTime = new AtomicLong();

    public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        log.info(String.format("Thread %s: start %s", t, r));
        startTime.set(System.nanoTime());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        try {
            long endTime = System.nanoTime();
            long taskTime = endTime - startTime.get();
            numTasks.incrementAndGet();
            totalTime.addAndGet(taskTime);
            log.info(String.format("Thread %s: end %s, time=%dns",
                    t, r, taskTime));
        } finally {
            super.afterExecute(r, t);
        }
    }

    @Override
    protected void terminated() {
        try {
            log.info(String.format("Terminated: avg time=%dns",
                    totalTime.get() / numTasks.get()));
        } finally {
            super.terminated();
        }
    }
}
</pre>

### 递归算法的并行性
如果在循环体重包含了一些密集计算，或者需要执行可能阻塞的I/O操作，那么只要每次迭代是独立的，都可以对其进行并行化。

如果循环中的迭代操作都是独立的，并且不需要等待所有的迭代操作都完成再继续执行，那么就可以使用Executor将串行循环转化为并行循环。

当串行循环中的各个迭代操作之间彼此独立，并且每个迭代操作执行的工作量比管理一个新任务时带来的开销更多，那么这个串行循环就适合并行化。

*将串行递归转换为并行递归*

<pre>
public abstract class TransformingSequential {

    void processSequentially(List<Element> elements) {
        for (Element e : elements)
            process(e);
    }

    void processInParallel(Executor exec, List<Element> elements) {
        for (final Element e : elements)
            exec.execute(new Runnable() {
                public void run() {
                    process(e);
                }
            });
    }

    public abstract void process(Element e);


    public <T> void sequentialRecursive(List<Node<T>> nodes,
                                        Collection<T> results) {
        for (Node<T> n : nodes) {
            results.add(n.compute());
            sequentialRecursive(n.getChildren(), results);
        }
    }

    public <T> void parallelRecursive(final Executor exec,
                                      List<Node<T>> nodes,
                                      final Collection<T> results) {
        for (final Node<T> n : nodes) {
            exec.execute(new Runnable() {
                public void run() {
                    results.add(n.compute());
                }
            });
            parallelRecursive(exec, n.getChildren(), results);
        }
    }

    public <T> Collection<T> getParallelResults(List<Node<T>> nodes)
            throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        Queue<T> resultQueue = new ConcurrentLinkedQueue<T>();
        parallelRecursive(exec, nodes, resultQueue);
        exec.shutdown();
        exec.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        return resultQueue;
    }

    interface Element {
    }

    interface Node <T> {
        T compute();

        List<Node<T>> getChildren();
    }
}
</pre>