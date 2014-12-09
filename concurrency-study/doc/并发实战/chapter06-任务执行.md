### 在线程中执行任务
*串行执行任务*

<pre>
public class SingleThreadWebServer {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            Socket connection = socket.accept();
            handleRequest(connection);
        }
    }

    private static void handleRequest(Socket connection) {
        // request-handling logic here
    }
}
</pre>

*显式地为任务创建线程*

<pre>
public class ThreadPerTaskWebServer {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            final Socket connection = socket.accept();
            Runnable task = new Runnable() {
                public void run() {
                    handleRequest(connection);
                }
            };
            new Thread(task).start();
        }
    }

    private static void handleRequest(Socket connection) {
        // request-handling logic here
    }
}
</pre>

#### 无限制创建线程的不足

    线程生命周期的开销。线程的创建与关闭不是‘免费’的。实际上的开销依据不同平台而不同，但是创建线程的确需要时间，带来处理请求的延迟，并且需要在JVM和操作系统之间进行相应的处理活动。如果请求是频繁的且轻量的，就像大多数服务器程序一样，那么为每个请求创建一个线程的做饭就会消耗大量的计算资源。
    
    资源消耗量。活动线程会消耗系统资源，尤其是内存。如果可运行的线程数多余可用的服务器数，线程将会空闲。大量空闲线程占用更多内存，给垃圾回收器带来压力，而且大量线程在竞争CPU资源时，还会产生其他的性能开销。如果你已经有了足够多的线程保持所有CPU忙碌，那么再创建更多的线程是有百害而无一例的。
    
    稳定性。应该限制可创建线程的数目。限制的数目依不同的平台而定，同时也受到JVM启动参数、Thread的构造函数中请求的栈大小等因素的影响，以及底层操作系统线程的限制，最可能的结果是收到一个OutOfMemoryError。企图从这种错误中恢复是非常危险的；更简单的办法是构造你的程序时避免超出这些限制。

在一定范围内，增加线程可以提高系统的吞吐量，一旦超出了这个范围，再创建更多的线程指挥拖垮你的程序。而一味地创建一个线程，会导致应用程序面临奔溃。为了摆脱这种危险，应该设置一个范围来限制你的应用程序可以创建的线程数，然后彻底地测试你的应用程序，确保即使线程数达到了这个范围的极限，程序也不至于耗尽所有的资源

### Executor框架

任务是一组逻辑工作单元，而线程则是使任务异步执行的机制。

Executor基于生产者和消费者模式，提交任务的操作相当于生产者（生成待完成的工作单元），执行任务的线程则相当于消费者（执行完这些工作单元）。如果要在程序中实现一个消费者-生产者的设计，那么最简单的方式通常就是使用Executor。

*基于Executor的Web服务器*

<pre>
public class TaskExecutionWebServer {

    private static final int NTHREADS = 100;
    private static final Executor exec = Executors.newFixedThreadPool(100);

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            final Socket connection = socket.accept();
            Runnable task = new Runnable() {
                public void run() {
                    handleRequest(connection);
                }
            };
            exec.execute(task);
        }
    }

    private static void handleRequest(Socket connection) {
        // request-handling logic here
    }
}
</pre>

我们可以很容易地将TaskExecutionWebServer修改为类似ThreadPerTaskWebServer的行为，只需使用一个为每个请求都创建新线程的Executor。

*为每个请求启动一个新线程的Executor*

<pre>
public class ThreadPerTaskExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }
}
</pre>

*在调用线程中以同步方式执行所有任务的Executor*

<pre>
public class WithInThreadExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
</pre>

#### 执行策略

将任务的提交与任务的执行体进行解耦，它的价值在于让你可以简单地为一个类给定任务制定执行策略，并且保证后续的修改保证不至于太困难。一个执行策略指明了任务执行的“what，where，when，how”几个因素，具体包括:

    任务在什么（what）线程中执行
    任务以什么（what）顺序执行（FIFO，LIFO，优先级）？
    可以有多少个（How Many）任务并发执行？
    可以有多少个（How Many）任务进入等待执行队列？
    如果系统过载，需要放弃一个任务，应该挑选哪一个（Which）任务？另外，如何（How）通知应用程序知道这一切？
    在一个任务的执行前与结束后，应该做什么(What)处理。

各种执行策略都是一种资源管理工具，最佳策略取决于可用的计算资源以及对服务质量的需求。
通过限制并发任务的数量，可用确保应用程序不会由于资源耗尽而失败，或者由于在稀缺资源上发生竞争而严重影响性能。
通过将任务的提交与任务的执行策略分离开来，有助于在部署阶段与可用硬件资源最匹配的执行策略。

#### 线程池

“在线程池”中执行任务比“为每个任务分配一个线程”优势更多。通过重用现有的线程而不是创建新线程，可以在处理多个请求时分摊在线程创建和撤销过程中产生的巨大开销。
另外一个额外的好处是，当请求到达时，工作线程通常已经存在，因此不会由于等待创建线程而延迟任务的执行，从而提高了响应性。
通过适当调整线程池的大小，可以创建足够多的线程以便使处理器保持忙碌状态，同时还可以防止过多线程相互竞争资源而使应用程序耗尽内存或失败。

Executors中的静态方法提供了线程池的创建方式

1.newFixedThreadPool. 创建一个固定长度的线程池，每当提交一个任务时就创建一个线程，知道达到线程池的最大量，这时线程池的规模将不再变化（如果某个线程由于发生了未预期的Exception而结束，那么线程池会补充一个新的线程）。
 
2.newCachedThreadPool。创建一个可缓存的线程池，调用execute 将重用以前构造的线程（如果线程可用）。如果现有线程没有可用的，则创建一个新线程并添加到池中。终止并从缓存中移除那些已有 60 秒钟未被使用的线程。线程池的规模不存在任何限制。 
 
3.newSingleThreadExecutor. 是一个单线程的Executor,它创建单个工作者线程来执行任务，如果这个线程异常技术，会创建另一个线程来替代。此线程池能确保依照任务在队列中的顺序来串行执行（如FIFO,LIFO，优先级）。

4.newScheduledThreadPool。创建一个固定长度的线程池，而且以延迟或定时的方式来执行任务，类似于Timer。

通过使用Executor，可以实现各种调优、管理、监视、记录日志、错误报告和其他功能，如果不使用任务执行框架，那么要增加这些功能是非常困难的。

#### Executor的生命周期

Executor的实现通常会创建线程来执行任务，但JVM只有在所有（非守护）线程全部终止后才会退出，因此，如果无法正确关闭Executor，那么jvm将无法结束。

由于Executor以异步方式来执行任务，因此在任何时刻，之前提交任务的状态不是立即可见的。
当关闭应用程序时

