java没有提供任何机制来安全地终止线程。但它提供了中断，这是一种协作机制，能够使一个线程终止另一个线程的当前工作。

### 任务取消

如果外部代码能够在某个操作正常完成之前将其置入“完成”状态，那么这个操作就可以称为可取消的。

在java中没有一种安全的抢占机制来停止线程，因此也就没有安全的抢占式方法来停止任务。只有一些协作式的机制，是请求取消的任何和代码都遵循一种协商好的协议。

其中一种协作机制能**设置某个“已请求取消”标志**，而任务将定期地查看该标志。如果设置了这个标志，那么任务将提前取消

*设置“已请求取消”标志*

<pre>
@ThreadSafe
public class PrimeGenerator implements Runnable {
    private static ExecutorService exec = Executors.newCachedThreadPool();

    @GuardedBy("this") private final List<BigInteger> primes
            = new ArrayList<BigInteger>();
    private volatile boolean cancelled;

    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!cancelled) {
            p = p.nextProbablePrime();
            synchronized (this) {
                primes.add(p);
            }
        }
    }

    public void cancel() {
        cancelled = true;
    }

    public synchronized List<BigInteger> get() {
        return new ArrayList<BigInteger>(primes);
    }

    static List<BigInteger> aSecondOfPrimes() throws InterruptedException {
        PrimeGenerator generator = new PrimeGenerator();
        exec.execute(generator);
        try {
            TimeUnit.SECONDS.sleep(1);
        } finally {
            generator.cancel();
        }
        return generator.get();
    }
}
</pre>

一个可取消的任务必须拥有取消策略，在这个策略中将详细地定义取消操作的“How”、“When”、“What”，即其他代码如何（How）请求取消该任务，任务在何时(When)检查是否已经请求了取消，已经在响应取消请求时应该执行哪些（What）操作。

#### 中断

http://www.ibm.com/developerworks/cn/java/j-jtp05236.html

PrimeGenerator中的取消机制最终会使得搜索素数的任务退出，但在退出过程中需要花费一定的时间。
然而，如果使用这种方法的任务调用了一个阻塞方法，例如：BlockingQueue.put，那么可能会产生一个更严重的问题——任务可能永远不会检查取消标志，因此永远不会结束。

*不可靠的取消操作将把生产者置于阻塞的操作中*

<pre>
class BrokenPrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;
    private volatile boolean cancelled = false;

    BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!cancelled)
                queue.put(p = p.nextProbablePrime());
        } catch (InterruptedException consumed) {
        }
    }

    public void cancel() {
        cancelled = true;
    }
}
</pre>

一些特殊的阻塞库的方法支持中断。线程中断是一种协作机制，线程可以通过这种机制来通知另一个线程，告诉它在合适的或者可能的情况下停止当前工作，并转而执行其他的工作。

每个线程都有一个boolean类型的中断状态。当中断线程时，这个线程的中断状态将被设置为true。在Thread中包含了中断线程以及查询线程中断状态的方法。

- interrupt方法能中断目标线程
- isInterrrupted方法能返回目标线程的中断状态
- 静态的interrupted方法将清除当前线程的中断状态，并返回它之前的值，这也是清除中断状态的唯一方法。

阻塞库方法，例如Thread.sleep和Object.wait等，都会检查线程何时中断，并且在发现中断时提前返回。它们在响应中断时执行的操作包括：**清除中断状态，抛出InterruptedException，表示阻塞操作由于中断而提前结束**。
JVM并不能保证阻塞方法检测到中断的速度，但在实际情况中响应速度还是非常快的。

当线程在非阻塞状态下中断时，它的中断状态将被设置，任何根据将被取消的操作来检查中断状态以判断发生了中断。如果不触发InterruptedException，那么中断状态将一直保持，知道明确地清除中断状态。

*调用interrupt并不意味着立即停止目标线程正在进行的工作，而只是传递了请求中断的消息。*中断并不会真正地中断一个正在运行的线程，而只是发出中断请求，然后由线程在下一个合适的时刻中断自己。

**中断是实现取消的最合理方式**

*使用中断来取消*

<pre>
public class PrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;

    PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!Thread.currentThread().isInterrupted())
                queue.put(p = p.nextProbablePrime());
        } catch (InterruptedException consumed) {
            /* Allow thread to exit */
        }
    }

    public void cancel() {
        interrupt();
    }
}
</pre>

#### 中断策略
中断策略规定线程如何处理某个中断请求--当发现中断的请求时，应该做哪些工作。规定哪些工作单元对于中断来说是原子操作，以及以多快的速度来响应中断。

**无论任务把中断视为取消，还是其他某个中断响应操作，都应该小心地保存执行线程的中断状态。**如果除了将InterruptedException传递给调用者外还需要执行其他操作，那么应该在捕获InterruptedException之后恢复中断状态：<code>Thread.currentThread().interrupt(); </code>

#### 响应中断

当调用可中断的阻塞函数时，例如Thread.sleep和BlockingQueue.put等，有两种实用策略可用于处理InterruptedException

- 传递异常，从而使你的方法也称为可中断的阻塞方法
- 恢复中断状态，从而使调用栈中的上层代码能够对其进行处理。

*只有实现了线程中断策略的代码才可以屏蔽中断请求。在常规的任务和库代码中都不应该屏蔽中断请求*

*不可取消的任务在退出前恢复中断*

<pre>
public class NoncancelableTask {
    public Task getNextTask(BlockingQueue<Task> queue) {
        boolean interrupted = false;
        try {
            while (true) {
                try {
                    return queue.take();
                } catch (InterruptedException e) {
                    interrupted = true;
                    // fall through and retry
                }
            }
        } finally {
            if (interrupted)
                Thread.currentThread().interrupt();
        }
    }

    interface Task {
    }
}
</pre>

#### 通过Future来实现取消
Future拥有一个Cancel方法。该方法带有一个Boolean类型的参数mayinterruptIfRunning,表示取消操作是否成功 。（这只是表示任务是否能够接收中断，而不是表示任务是否能检测并处理中断）。如果mayinterruptIfRunning为true并且任务当前正在某个线程中运行，那么 这个线程能被中断，如果 这个参数为false，那么意味着若任务还没有启动，就不要运行它。这种方法应用于那些 不处理中断的任务中。

*通过Future来取消任务*

<pre>
public class TimedRun {
    private static final ExecutorService taskExec = Executors.newCachedThreadPool();

    public static void timedRun(Runnable r,
                                long timeout, TimeUnit unit)
            throws InterruptedException {
        Future<?> task = taskExec.submit(r);
        try {
            task.get(timeout, unit);
        } catch (TimeoutException e) {
            // task will be cancelled below
        } catch (ExecutionException e) {
            // exception thrown in task; rethrow
            throw LaunderThrowable.launderThrowable(e.getCause());
        } finally {
            //如果任务已经结束，那么执行取消操作也不会带来任何影响
            task.cancel(true); // 如果任务正在运行，那么将被中断
        }
    }
}
</pre>

*当Future.get抛出InterruptedException或TimeoutException时，如果你知道不再需要结果，那么就可以调用Future.cancel来取消任务*

#### 处理不可中断的阻塞

在java库中，许多可阻塞的方法都是通过提前返回或者抛出InterruptedException来响应中断请求的，从而使开发人员更容易构建出能响应取消请求的任务。然而并非所有的可阻塞方法或者阻塞机制都能响应中断；如果一个线程由于执行同步的Socket I/O或者等待获得内置锁而阻塞，那么中断请求只能设置线程的中断状态，除此之外没有其他任何作用。

以下是不可中断阻塞的情况：

    java.io包中的同步Socket I/O
    java.io包中的同步I/O
    Selector的异步I/O
    获取某个锁

*通过改写interrupte方法将非标准的取消操作封装在Thread中*

<pre>
public class ReaderThread extends Thread {
    private static final int BUFSZ = 512;
    private final Socket socket;
    private final InputStream in;

    public ReaderThread(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
    }

    public void interrupt() {
        try {
            socket.close();
        } catch (IOException ignored) {
        } finally {
            super.interrupt();
        }
    }

    public void run() {
        try {
            byte[] buf = new byte[BUFSZ];
            while (true) {
                int count = in.read(buf);
                if (count < 0)
                    break;
                else if (count > 0)
                    processBuffer(buf, count);
            }
        } catch (IOException e) { /* Allow thread to exit */
        }
    }

    public void processBuffer(byte[] buf, int count) {
    }
}
</pre>

#### 采用newTaskFor来封装非标准的取消

<pre>
public abstract class SocketUsingTask <T> implements CancellableTask<T> {
    @GuardedBy("this") private Socket socket;

    protected synchronized void setSocket(Socket s) {
        socket = s;
    }

    public synchronized void cancel() {
        try {
            if (socket != null)
                socket.close();
        } catch (IOException ignored) {
        }
    }

    public RunnableFuture<T> newTask() {
        return new FutureTask<T>(this) {
            public boolean cancel(boolean mayInterruptIfRunning) {
                try {
                    SocketUsingTask.this.cancel();
                } finally {
                    return super.cancel(mayInterruptIfRunning);
                }
            }
        };
    }
}


interface CancellableTask <T> extends Callable<T> {
    void cancel();

    RunnableFuture<T> newTask();
}


@ThreadSafe
class CancellingExecutor extends ThreadPoolExecutor {
    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        if (callable instanceof CancellableTask)
            return ((CancellableTask<T>) callable).newTask();
        else
            return super.newTaskFor(callable);
    }
}
</pre>

### 停止基于线程的服务

**除非拥有某个线程，否则不能对该线程进行操控**

线程有一个相应的拥有者，即创建该线程的类。因此线程池是其工作线程的所有者，如果要中断这些线程，那么应该使用线程池。

线程的所有权是不可传递的：应用程序可以拥有服务，服务也可以拥有工作者线程，但应用程序不能拥有工作者线程，因此应用程序不能直接停止工作者线程。
相反，服务应该提供生命周期方法来关闭它自己以及它所拥有的线程。这样，当应用程序关闭该服务时，服务就可以关闭所有的线程了。

*对于持有线程的服务，只要服务的存在时间大于创建线程的方法的存在时间，那么就应该提供生命周期方法。*

*不支持关闭的生产者——消费者日志服务*

<pre>
public class LogWriter {

    private final BlockingQueue<String> queue;
    private final LoggerThread logger;
    private static final int CAPACITY = 1000;

    public LogWriter(Writer writer) {
        this.queue = new LinkedBlockingQueue<String>(CAPACITY);
        this.logger = new LoggerThread(writer);
    }

    public void start() {
        logger.start();
    }

    public void log(String msg) throws InterruptedException {
        queue.put(msg);
    }

    private class LoggerThread extends Thread {
        private final PrintWriter writer;

        public LoggerThread(Writer writer) {
            this.writer = new PrintWriter(writer, true); // autoflush
        }

        public void run() {
            try {
                while (true)
                    writer.println(queue.take());
            } catch (InterruptedException ignored) {
            } finally {
                writer.close();
            }
        }
    }

}
</pre>

如果只是使日志线程退出，并不是一种完备的关闭机制。这种直接关闭的做法会丢失那些正在等待被写入到日志的消息，不仅如此，其他线程将在调用log时被阻塞，因为消息队列是满的，因此这些线程将无法解除阻塞状态。
当取消一个生产者—消费者操作时，需要同时取消生产者和消费者。

*向LogWriter添加可靠的取消操作*

<pre>
public class LogService {

    private final BlockingQueue<String> queue;
    private final LoggerThread logger;
    private static final int CAPACITY = 1000;

    @GuardedBy("this") private boolean isShutdown;
    @GuardedBy("this") private int reservations;

    public LogService(Writer writer) {
        this.queue = new LinkedBlockingQueue<String>(CAPACITY);
        this.logger = new LoggerThread(writer);
    }

    public void start() {
        logger.start();
    }

    public void stop() {
        synchronized (this) {
            isShutdown = true;
        }
        logger.interrupt();
    }

    public void log(String msg) throws InterruptedException {
        synchronized (this) {
            if (isShutdown) {
                throw new IllegalStateException();
            }
            ++ reservations;
        }
        queue.put(msg);
    }

    private class LoggerThread extends Thread {
        private final PrintWriter writer;

        public LoggerThread(Writer writer) {
            this.writer = new PrintWriter(writer, true); // autoflush
        }

        public void run() {
            try {
                while (true) {
                    try {
                        synchronized (LogService.class) {
                            if (isShutdown && reservations == 0) {
                                break;
                            }
                        }
                        String msg = queue.take();
                        synchronized (LogService.this) {
                            -- reservations;
                        }
                        writer.println(msg);
                    } catch (InterruptedException e) {
                         /* retry */
                    }
                }
            } finally {
                writer.close();
            }
        }
    }

}
</pre>

#### 关闭ExecutorService
shutdonwNow强行关闭首先关闭当前正在执行的任务，然后返回所有尚未启动的任务清单。强行关闭的速度更快，但风险也更大，因为任务可能在执行到一半时被结束；而正常关闭虽然速度慢，但却更安全，因为EexcutorService会一直等到队列中的所有任务都执行完成后才关闭。

*使用ExecutorService的日志服务*

<pre>
public class LogService2 {

    private PrintWriter writer;

    private final ExecutorService exec = Executors.newSingleThreadExecutor();

    public void start() {

    }

    public void stop() throws InterruptedException {
        try {
            exec.shutdown();
            exec.awaitTermination(3, TimeUnit.SECONDS);
        } finally {
            writer.close();
        }
    }

    public void log(String msg) throws InterruptedException {
        try {
            exec.execute(new WriteTask(msg));
        } catch (RejectedExecutionException e) {

        }
    }

    private class WriteTask implements Runnable {
        private String msg;

        private WriteTask(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            writer.println(msg);
        }
    }

}
</pre>

#### “毒丸”对象
另一种关闭生产者—消费者服务的方式就是使用“毒丸”对象：“毒丸”是指一个放在队列上的对象，其含义是：“当得到这个对象时，立即停止”。

<pre>
public class IndexingService {
    private static final int CAPACITY = 1000;
    private static final File POISON = new File("");
    private final IndexerThread consumer = new IndexerThread();
    private final CrawlerThread producer = new CrawlerThread();
    private final BlockingQueue<File> queue;
    private final FileFilter fileFilter;
    private final File root;

    public IndexingService(File root, final FileFilter fileFilter) {
        this.root = root;
        this.queue = new LinkedBlockingQueue<File>(CAPACITY);
        this.fileFilter = new FileFilter() {
            public boolean accept(File f) {
                return f.isDirectory() || fileFilter.accept(f);
            }
        };
    }

    private boolean alreadyIndexed(File f) {
        return false;
    }

    class CrawlerThread extends Thread {
        public void run() {
            try {
                crawl(root);
            } catch (InterruptedException e) { /* fall through */
            } finally {
                while (true) {
                    try {
                        queue.put(POISON);
                        break;
                    } catch (InterruptedException e1) { /* retry */
                    }
                }
            }
        }

        private void crawl(File root) throws InterruptedException {
            File[] entries = root.listFiles(fileFilter);
            if (entries != null) {
                for (File entry : entries) {
                    if (entry.isDirectory())
                        crawl(entry);
                    else if (!alreadyIndexed(entry))
                        queue.put(entry);
                }
            }
        }
    }

    class IndexerThread extends Thread {
        public void run() {
            try {
                while (true) {
                    File file = queue.take();
                    if (file == POISON)
                        break;
                    else
                        indexFile(file);
                }
            } catch (InterruptedException consumed) {
            }
        }

        public void indexFile(File file) {
            /*...*/
        };
    }

    public void start() {
        producer.start();
        consumer.start();
    }

    public void stop() {
        producer.interrupt();
    }

    public void awaitTermination() throws InterruptedException {
        consumer.join();
    }
}
</pre>

只有在生产者和消费者的数量都已知的情况下，才可以使用“毒丸”对象。在IndexingService中采用的解决方案可以扩展到多个生产者：只需要每个生产者都向队列中烦人一个“毒丸”对象，
并且消费者仅当在接收到N（生产者数量）个“毒丸”对象时才停止。这种方法也可以扩展到多个消费者的情况，只需要生产者将N（消费者数量）个“毒丸”放入队列。

#### 只执行一次的服务
如果某个方法需要处理一批任务，并且当所有任务都处理完成后才返回，那么可以通过一个私有的Executor来简化服务的生命周期管理，其中该Executor的生命周期是由这个方法来控制的。

<pre>
public class CheckForMail {
    public boolean checkMail(Set<String> hosts, long timeout, TimeUnit unit)
            throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        final AtomicBoolean hasNewMail = new AtomicBoolean(false);
        try {
            for (final String host : hosts)
                exec.execute(new Runnable() {
                    public void run() {
                        if (checkMail(host))
                            hasNewMail.set(true);
                    }
                });
        } finally {
            exec.shutdown();
            exec.awaitTermination(timeout, unit);
        }
        return hasNewMail.get();
    }

    private boolean checkMail(String host) {
        // Check for mail
        return false;
    }
}
</pre>

*在ExecutorService中跟踪在关闭之后被取消的任务*

<pre>
public class TrackingExecutor extends AbstractExecutorService {
    private final ExecutorService exec;
    private final Set<Runnable> tasksCancelledAtShutdown =
            Collections.synchronizedSet(new HashSet<Runnable>());

    public TrackingExecutor(ExecutorService exec) {
        this.exec = exec;
    }

    public void shutdown() {
        exec.shutdown();
    }

    public List<Runnable> shutdownNow() {
        return exec.shutdownNow();
    }

    public boolean isShutdown() {
        return exec.isShutdown();
    }

    public boolean isTerminated() {
        return exec.isTerminated();
    }

    public boolean awaitTermination(long timeout, TimeUnit unit)
            throws InterruptedException {
        return exec.awaitTermination(timeout, unit);
    }

    public List<Runnable> getCancelledTasks() {
        if (!exec.isTerminated())
            throw new IllegalStateException(/*...*/);
        return new ArrayList<Runnable>(tasksCancelledAtShutdown);
    }

    public void execute(final Runnable runnable) {
        exec.execute(new Runnable() {
            public void run() {
                try {
                    runnable.run();
                } finally {
                    if (isShutdown()
                            && Thread.currentThread().isInterrupted())
                        tasksCancelledAtShutdown.add(runnable);
                }
            }
        });
    }
}
</pre>

### 处理非正常的线程终止

*将异常写入日志的UncaughtExceptionHandler*

<pre>
public class UEHLogger implements Thread.UncaughtExceptionHandler {
    public void uncaughtException(Thread t, Throwable e) {
        Logger logger = Logger.getAnonymousLogger();
        logger.log(Level.SEVERE, "Thread terminated with exception: " + t.getName(), e);
    }
}
</pre>

要为线程池中的所有线程设置一个UncaughtExceptionHandler，需要为ThreadPoolExecutor的构造函数提供一个ThreadFactory。

只有通过execute提交的任务，才能将它抛出的异常交给未捕获异常处理器，而通过submit提交的任务，无论是抛出未检查异常还是已检查异常，都将被认为是任务返回的一部分。
如果一个由submit提交的任务由于抛出了异常而结束，那么这个异常将被Future.get封装在ExecutionException中重新抛出。

### JVM关闭
#### 关闭钩子
关闭钩子可以用于实现服务或者应用程序的清理工作，例如删除临时文件，或者清除无法由操作系统自动清除的资源。

<pre>
public class ShutdonwHook {

    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    LogService.this.stop();
                } catch (InterruptedException e) {
                    
                }
            }
        });
    }
}
</pre>

#### 守护线程
线程可分为两种：普通线程和守护线程。在JVM启动时创建的所有线程中，除了主线程以外，其他的线程都是守护线程。
当创建一个新线程时，新线程将继承创建它的线程的守护状态，因此在默认情况下，主线程创建的所有线程都是普通线程。

#### 终结器