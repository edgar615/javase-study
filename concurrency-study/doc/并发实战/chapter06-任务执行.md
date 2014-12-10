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

为了解决执行服务的生命周期问题，Executor扩展了ExecutorService接口，添加了一些用于生命周期管理的方法（同时还有一些用于任务提交的遍历方法）

ExecutorService的生命周期有3种状态：运行、关闭和已终止。
ExecutorService在初始创建时处于运行状态。
shutdown方法将执行平缓的关闭过程：不再接受新任务，同时等待已久提交的任务执行完成——包括那些还未开始执行的任务。
shutdownNow方法将执行粗暴的关闭过程：它将尝试取消所有运行中的任务，并且不再启动队列中尚未开始执行的任务。

在ExecutorService关闭后提交的任务将有“拒绝执行处理器（Rejected Execution Handler）来处理”，它会抛弃任务，或者使得execute方法抛出一个未检查的RejectedExecutionException。
等所有的任务都完成后，ExecutorService将转入终止状态。可以调用awaitTermination来等待Execution到达终止状态，或者通过调用isTerminated来轮询ExecutorService是否已经终止。

*支持关闭操作的Web服务器*

<pre>
public class LifecycleWebServer {

    private static final int NTHREADS = 100;
    private static final ExecutorService exec = Executors.newFixedThreadPool(100);

    public void start() throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (!exec.isShutdown()) {
            try {
                final Socket connection = socket.accept();
                Runnable task = new Runnable() {
                    public void run() {
                        handleRequest(connection);
                    }
                };
                exec.execute(task);
            } catch (RejectedExecutionException e) {
                if (!exec.isShutdown()) {
                    System.out.println("task submission rejected");
                }
            }
        }
    }

    public void stop() {
        exec.shutdown();
    }

    private void handleRequest(Socket connection) {
        Request request = readRequest(connection);
        if (isShutdownRequest(request)) {
            stop();
        } {
            dispatchRequest(request);
        }
    }


    interface Request {
    }

    private Request readRequest(Socket s) {
        return null;
    }

    private void dispatchRequest(Request r) {
    }

    private boolean isShutdownRequest(Request r) {
        return false;
    }
}
</pre>

#### 延迟任务与周期任务

Timer类负责管理延迟任务以及周期任务。然而Timer存在一些缺陷，因此应该考虑使用ScheduledThreadPoolExecutor来代替它。

    Timer在执行所有定时任务时只会创建一个线程。如果某个任务的执行是时间过长，那么将破坏其他TimerTask的定时精确性。
    
    Timer计时器有管理任务延迟执行("如1000ms后执行任务")以及周期性执行("如每500ms执行一次该任务")。但是，Timer存在一些缺陷，因此你应该考虑使用ScheduledThreadPoolExecutor作为代替品,Timer对调度的支持是基于绝对时间,而不是相对时间的，由此任务对系统时钟的改变是敏感的;ScheduledThreadExecutor只支持相对时间。
    
    Timer的另一个问题在于，如果TimerTask抛出未检查的异常，Timer将会产生无法预料的行为。Timer线程并不捕获异常，所以TimerTask抛出的未检查的异常会终止timer线程。这种情况下，Timer也不会再重新恢复线程的执行了;它错误的认为整个Timer都被取消了。此时，已经被安排但尚未执行的TimerTask永远不会再执行了，新的任务也不能被调度了。

可以参考http://www.cnblogs.com/chenssy/p/3788407.html

*错误的Timer行为*
<pre>
public class TimerTest {
    private Timer timer = new Timer();
 
    // 启动计时器
    public void lanuchTimer() {
        timer.schedule(new TimerTask() {
            public void run() {
                throw new RuntimeException();
            }
        }, 1000 * 3, 500);
    }
 
    // 向计时器添加一个任务
    public void addOneTask() {
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("hello world");
            }
        }, 1000 * 1, 1000 * 5);
    }
 
    public static void main(String[] args) throws Exception {
        TimerTest test = new TimerTest();
        test.lanuchTimer();
        Thread.sleep(1000 * 5);// 5秒钟之后添加一个新任务
        test.addOneTask();
    }
}
</pre>

输出
    Exception in thread "Timer-0" java.lang.RuntimeException
    	at concurrencyinpractice.chapter06.TimerTest$1.run(TimerTest.java:13)
    	at java.util.TimerThread.mainLoop(Timer.java:555)
    	at java.util.TimerThread.run(Timer.java:505)
    Exception in thread "main" java.lang.IllegalStateException: Timer already cancelled.
    	.....

可以通过ScheduledThreadPoolExecutor的构造函数或newScheduledThreadPool的工厂方法来创建该类的对象。

ScheduledThreadPoolExecutor它可另行安排在给定的延迟后运行命令，或者定期执行命令。需要多个辅助线程时，或者要求 ThreadPoolExecutor 具有额外的灵活性或功能时，此类要优于 Timer。

- schedule(Callable<V> callable, long delay, TimeUnit unit) 创建并执行在给定延迟后启用的 ScheduledFuture。
- schedule(Runnable command, long delay, TimeUnit unit) 创建并执行在给定延迟后启用的一次性操作。
- scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) 创建并执行一个在给定初始延迟后首次启用的定期操作，后续操作具有给定的周期；也就是将在 initialDelay 后开始执行，然后在 initialDelay+period 后执行，接着在 initialDelay + 2 * period 后执行，依此类推
- scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) 创建并执行一个在给定初始延迟后首次启用的定期操作，随后，在每一次执行终止和下一次执行开始之间都存在给定的延迟。


*延迟后执行*

<pre>
ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
Future<String> future =executorService.schedule(new Callable<String>() {
    @Override
    public String call() throws Exception {
        System.out.println("Executed1!");
        return "Called1!";
    }
}, 5, TimeUnit.SECONDS);
Future<String> future2 =executorService.schedule(new Callable<String>() {
    @Override
    public String call() throws Exception {
        System.out.println("Executed2!");
        return "Called2!";
    }
}, 3, TimeUnit.SECONDS);
System.out.println(future.get());
System.out.println(future2.get());
executorService.shutdown();
</pre>

输出：
    Executed2!
    Executed1!
    Called1!
    Called2!
    
*定时任务*

<pre>
ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
executorService.scheduleAtFixedRate(new Runnable() {
    @Override
    public void run() {
        System.out.println("Executed! " + new Date());
    }
}, 5, 3, TimeUnit.SECONDS);
TimeUnit.SECONDS.sleep(15);
executorService.shutdown();
</pre>

输出：
    Executed! Wed Dec 10 11:33:03 CST 2014
    Executed! Wed Dec 10 11:33:06 CST 2014
    Executed! Wed Dec 10 11:33:09 CST 2014
    Executed! Wed Dec 10 11:33:12 CST 2014
    
*定期任务，每一次执行终止和下一次执行开始之间都存在给定的延迟*    

<pre>
ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
executorService.scheduleWithFixedDelay(new Runnable() {
    @Override
    public void run() {
        System.out.println("Executed! " + new Date());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
        }
    }
}, 5, 3, TimeUnit.SECONDS);
TimeUnit.SECONDS.sleep(15);
executorService.shutdown();
</pre>

输出：
    Executed! Wed Dec 10 11:34:52 CST 2014
    Executed! Wed Dec 10 11:34:56 CST 2014
    Executed! Wed Dec 10 11:35:00 CST 2014

**如果要构建自己的调度服务，可以使用DelayQueue**，它实现了BlockingQueue，并为ScheduleThreadPoolExecutor提供调度功能。
DelayQueue管理着一组Delayed对象。每个Delayed对象都有一个相应的延迟时间；在DelayQueue中，只有某个元素逾期后，才能从DelayQueue中执行take操作。
从DelayQueue中返回的对象将根据它们的延时时间进行排序。

### 找出可用的并行性

*串行的页面渲染器*

<pre>
public abstract class SingleThreadRenderer {
    void renderPage(CharSequence source) {
        renderText(source);
        List<ImageData> imageData = new ArrayList<ImageData>();
        for (ImageInfo imageInfo : scanForImageInfo(source))
            imageData.add(imageInfo.downloadImage());
        for (ImageData data : imageData)
            renderImage(data);
    }

    interface ImageData {
    }

    interface ImageInfo {
        ImageData downloadImage();
    }

    abstract void renderText(CharSequence s);
    abstract List<ImageInfo> scanForImageInfo(CharSequence s);
    abstract void renderImage(ImageData i);
}
</pre>

图像下载过程的大部分时间都在等待I/O操作执行完成，在这个期间CPU几乎不做任何工作。因此这种串行执行方法没有充分利用CPU，使得用户看到最终页面之前要等待过长的时间。
通过将问题分解为多个独立的任务并发执行，能够获得更高的CPU利用率和响应灵敏度。

#### Callable与Future

许多任务实际上都是存在延迟的计算——执行数据库查询，从网络上获取资源，或者计算某个复杂的功能。
对于这些任务，Callable是一种更好的抽象：它认为主入口点（即call）将返回一个值，并可能抛出一个异常（Callable<Void>表示无返回值的任务）。
在Executor中包含了一些辅助方法能将其他类型的任务封装为一个Callable。

Runnable和Callable描述的都是抽象的计算任务。这些任务通常是有范围的，即都有一个明确的起始点，并且最终会结束。
Executor执行的任务有4个生命周期阶段：创建、提交、开始和完成。
在Executor框架中，已提交但尚未开始的任务可以取消，但对于那些已经开始执行的任务，只有当它们能相应中断时，才能取消。
取消一个已经完成的任务不会有任何影响。

Future表示一个任务的声明周期，并提供了相应的方法来判断是否已经完成或取消，已经获取任务的结果和取消任务等。
在Future规范中包含的隐含意义是：任务的生命周期只能前进，不能后退，就像ExecutorService的生命周期一样。
当某个任务完成后，它就永远停留在“完成”的状态。

get方法的行为取决于任务的状态（尚未开始、正在进行、已完成），如果任务以及完成，那么get会立即返回或者抛出一个异常，如果任务没有完成，那么get将阻塞并直到任务完成。
如果任务抛出了异常，那么get将该异常封装为ExecutionException并重新抛出。
如果任务被取消，那么get将抛出CancellationException。
如果get抛出ExecutionException，可以通过getCause来获得被抛出的异常。

<pre>
public interface Callable<V> {
    V call() throws Exception;
}

public interface Future<V> {

    boolean cancel(boolean mayInterruptIfRunning);

    boolean isCancelled();

    boolean isDone();

    V get() throws InterruptedException, ExecutionException;

    V get(long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException;
}
</pre>

ExecutorService中所有的submit都将返回一个Future，从而将一个Runnable或Callable提交给Executor，并得到一个Future来获得任务的执行结果或者取消任务。
还可以显示的为某个特定的Runnable或Callable实现一个FutureTask。

*ThreadPoolExecutor中newTaskFor的默认实现*

<pre>
protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
    return new FutureTask<T>(runnable, value);
}
</pre>

*使用Future实现页面渲染器*

<pre>
public abstract class FutureRenderer {
    private final ExecutorService executor = Executors.newCachedThreadPool();

    void renderPage(CharSequence source) {
        final List<ImageInfo> imageInfos = scanForImageInfo(source);
        Callable<List<ImageData>> task =
                new Callable<List<ImageData>>() {
                    public List<ImageData> call() {
                        List<ImageData> result = new ArrayList<ImageData>();
                        for (ImageInfo imageInfo : imageInfos)
                            result.add(imageInfo.downloadImage());
                        return result;
                    }
                };

        Future<List<ImageData>> future = executor.submit(task);
        renderText(source);

        try {
            List<ImageData> imageData = future.get();
            for (ImageData data : imageData)
                renderImage(data);
        } catch (InterruptedException e) {
            // Re-assert the thread's interrupted status
            Thread.currentThread().interrupt();
            // We don't need the result, so cancel the task too
            future.cancel(true);
        } catch (ExecutionException e) {
            throw launderThrowable(e.getCause());
        }
    }

    interface ImageData {
    }

    interface ImageInfo {
        ImageData downloadImage();
    }

    abstract void renderText(CharSequence s);

    abstract List<ImageInfo> scanForImageInfo(CharSequence s);

    abstract void renderImage(ImageData i);
}
</pre>

*只有当大量相互独立且同构的任务可以并发进行处理时，才能将程序的工作负载分配到多个任务中带来真正的性能提升*

#### 完成服务CompletionService
CompletionService将Executor和BlockingQueue的功能耦合在一起。你可以将Callable任务提交给它来执行，任何使用类似于队列操作的take和poll等方法来获得已完成的结果，而这些结果会在完成时将被封装为Future。
ExecutorCompletionService实现了CompletionService，并将计算部分委托给一个Executor。

ExecutorCompletionService的实现非常简单。在构造函数中创建一个BlockingQueue来保存计算完成的结果。当计算完成时，调用Futrue-Task的done方法。
当提交某个任务时，该任务首先包装为一个QueueingFuture，这是FutureTask的一个子类，然后再改写子类的done方法，并将结果放入BlockingQueue中。

*QueueingFuture*

<pre>
private class QueueingFuture extends FutureTask<Void> {
    QueueingFuture(RunnableFuture<V> task) {
        super(task, null);
        this.task = task;
    }
    protected void done() { completionQueue.add(task); }
    private final Future<V> task;
}
</pre>

*CompletionService的示例*

<pre>
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
</pre>

*使用CompletionService实现页面渲染*

<pre>
public abstract class Renderer {
    private final ExecutorService executor;

    Renderer(ExecutorService executor) {
        this.executor = executor;
    }

    void renderPage(CharSequence source) {
        final List<ImageInfo> info = scanForImageInfo(source);
        CompletionService<ImageData> completionService =
                new ExecutorCompletionService<ImageData>(executor);
        for (final ImageInfo imageInfo : info)
            completionService.submit(new Callable<ImageData>() {
                public ImageData call() {
                    return imageInfo.downloadImage();
                }
            });

        renderText(source);

        try {
            for (int t = 0, n = info.size(); t < n; t++) {
                Future<ImageData> f = completionService.take();
                ImageData imageData = f.get();
                renderImage(imageData);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw launderThrowable(e.getCause());
        }
    }

    interface ImageData {
    }

    interface ImageInfo {
        ImageData downloadImage();
    }

    abstract void renderText(CharSequence s);

    abstract List<ImageInfo> scanForImageInfo(CharSequence s);

    abstract void renderImage(ImageData i);

}
</pre>

#### 为任务设置时限
有时候，如果某个任务无法再指定时间内完成，那么将不再需要它的结果，此时可以放弃这个任务。

在有限时间内执行任务的主要困难在于，要确保得到答案的时间不会超过限定时间，或者在限定的时间内无法获得答案。
在支持时间限制的Future.get中支持这种需求：当结果可用时，它将立即返回，如果在指定时限内没有计算出结果，那么将抛出TimeoutException。

在使用限时任务时需要注意，**当这些任务超时后应该立即停止**，从而避免为继续计算这个不再使用的结果而浪费计算资源。
要实现这个功能，可以由任务本身来管理它的限定时间并且在超时后中止执行或取消任务。
此时可以再次使用Future，如果一个限时的get方法抛出了TimeoutException，那么可以通过Future来取消任务。
如果编写的任务是可取消的，那么可以提前中止它，以免消耗过多的资源。

<pre>
public class RenderWithTimeBudget {
    private static final Ad DEFAULT_AD = new Ad();
    private static final long TIME_BUDGET = 1000;
    private static final ExecutorService exec = Executors.newCachedThreadPool();

    Page renderPageWithAd() throws InterruptedException {
        long endNanos = System.nanoTime() + TIME_BUDGET;
        Future<Ad> f = exec.submit(new FetchAdTask());
        // Render the page while waiting for the ad
        Page page = renderPageBody();
        Ad ad;
        try {
            // Only wait for the remaining time budget
            long timeLeft = endNanos - System.nanoTime();
            ad = f.get(timeLeft, NANOSECONDS);
        } catch (ExecutionException e) {
            ad = DEFAULT_AD;
        } catch (TimeoutException e) {
            ad = DEFAULT_AD;
            f.cancel(true);
        }
        page.setAd(ad);
        return page;
    }

    Page renderPageBody() { return new Page(); }


    static class Ad {
    }

    static class Page {
        public void setAd(Ad ad) { }
    }

    static class FetchAdTask implements Callable<Ad> {
        public Ad call() {
            return new Ad();
        }
    }

}
</pre>

#### invokeAll

invokeAll方法的参数为一组任务，并返回一组Future。这两个集合有着相同的结构。
invokeAll按照任务集合中迭代器的顺序将所有的Future添加到返回的集合中，从而使调用者能将Future与其表示的Callable关联起来。
当所有的任务都执行完毕时，或者调用线程被中断时，又或者超过指定时限时，invokeAll将返回。
当超过指定时限后，任何还未完成的任务都会取消。当invokeAll返回后，每个任务要么正常地完成，要么被取消，而客户端代码可以调用get或isCancelled来判断究竟是何种情况。

*invokeAll示例*

<pre>
public class InvokeAllExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Set<Callable<String>> callables = new HashSet<Callable<String>>();

        callables.add(new Callable<String>() {
            public String call() throws Exception {
                return "Task 1";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                return "Task 2";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                return "Task 3";
            }
        });

        List<Future<String>> futures = executorService.invokeAll(callables);

        for (Future<String> future : futures) {
            System.out.println("result = " + future.get());
        }

        executorService.shutdown();
    }
}
</pre>

*在预定时间内请求旅游报价*

<pre>
public class TimeBudget {
    private static ExecutorService exec = Executors.newCachedThreadPool();

    public List<TravelQuote> getRankedTravelQuotes(TravelInfo travelInfo, Set<TravelCompany> companies,
                                                   Comparator<TravelQuote> ranking, long time, TimeUnit unit)
            throws InterruptedException {
        List<QuoteTask> tasks = new ArrayList<QuoteTask>();
        for (TravelCompany company : companies)
            tasks.add(new QuoteTask(company, travelInfo));

        List<Future<TravelQuote>> futures = exec.invokeAll(tasks, time, unit);

        List<TravelQuote> quotes =
                new ArrayList<TravelQuote>(tasks.size());
        Iterator<QuoteTask> taskIter = tasks.iterator();
        for (Future<TravelQuote> f : futures) {
            QuoteTask task = taskIter.next();
            try {
                quotes.add(f.get());
            } catch (ExecutionException e) {
                quotes.add(task.getFailureQuote(e.getCause()));
            } catch (CancellationException e) {
                quotes.add(task.getTimeoutQuote(e));
            }
        }

        Collections.sort(quotes, ranking);
        return quotes;
    }

}

class QuoteTask implements Callable<TravelQuote> {
    private final TravelCompany company;
    private final TravelInfo travelInfo;

    public QuoteTask(TravelCompany company, TravelInfo travelInfo) {
        this.company = company;
        this.travelInfo = travelInfo;
    }

    TravelQuote getFailureQuote(Throwable t) {
        return null;
    }

    TravelQuote getTimeoutQuote(CancellationException e) {
        return null;
    }

    public TravelQuote call() throws Exception {
        return company.solicitQuote(travelInfo);
    }
}

interface TravelCompany {
    TravelQuote solicitQuote(TravelInfo travelInfo) throws Exception;
}

interface TravelQuote {
}

interface TravelInfo {
}
</pre>