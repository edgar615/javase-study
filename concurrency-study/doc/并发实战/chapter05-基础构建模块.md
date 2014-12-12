### 同步容器类

*Vector上可能导致混乱结果的复合操作*

<pre>
public class UnsafeVectorHelpers {
    public static Object getLast(Vector list) {
        int lastIndex = list.size() - 1;
        return list.get(lastIndex);
    }

    public static void deleteLast(Vector list) {
        int lastIndex = list.size() - 1;
        list.remove(lastIndex);
    }
}
</pre>

*使用客户端加锁的Vector上的复合操作*

<pre>
public class SafeVectorHelpers {
    public static Object getLast(Vector list) {
        synchronized (list) {
            int lastIndex = list.size() - 1;
            return list.get(lastIndex);
        }
    }

    public static void deleteLast(Vector list) {
        synchronized (list) {
            int lastIndex = list.size() - 1;
            list.remove(lastIndex);
        }
    }
}
</pre>

#### 迭代器
几乎所有的容器类都没有消除复合操作的问题，当它们发现在迭代过程中被修改时就会抛出ConcurrentModificationException异常。这种处理并不完备，只能作为并发容器的预警指示器，它们采用的实现方式是，将计数器的变化和容器关联起来，如果在迭代期间计数器被修改，那么 hasnext和next将抛出这个异常。如果容器规模很大，那么如果对其进行了客户端加锁之后，其它线程要等待很长时间，那么极大地降低吞吐量和CPU的利用率。

如果不希望在迭代期间对容器加锁，那么 一种替代方法就是“克隆”容器，并在副本上进行迭代。副本封闭在线程内，因此其它线程不会在迭代过程中对其进行修改。这样就避免抛出ConcurrentModificationException(在克隆过程中仍然需要对容器加锁)。在克隆容器时存在显著的性能开销。

#### 隐藏迭代器

<pre>
public class HiddenIterator {

	@GuardedBy("this")
    private final Set<Integer> set = new HashSet<Integer>();

	public synchronized void add(Integer i) {
		set.add(i);
	}

	public synchronized void remove(Integer i) {
		set.remove(i);
	}

	public void addTenThings() {
		Random r = new Random();
		for (int i = 0; i &lt; 10; i++) {
			add(r.nextInt());
		}
		System.out.println("DEBUG: added ten elements to " + set);
	}
}
</pre>

addTenThings方法可能会抛出ConcurrentModificationException，因为toString方法会对容器进行迭代。

如果状态与保护它的同步代码之间相隔越远，那么开发人员就越容易忘记在访问状态时使用正确的同步。
如果HiddenIterator用synchronizedSet来包装HashSet并且对同步代码进行封装，那么就不会发生这种错误。

当容器作为另一个容器的元素或者键值时，就会出现调用hashCode equals等方法，间接调用迭代操作，而可能会产生ConcurrentModificationException异常。同样containsAll、removeAll、retainAll等方法以及把容器作为参数的构造函数都会对容器进行迭代。

### 并发容器

ConcurrentHashMap -> HashMap

CopyOnWriteArrayList -> ArrayList

BlockingQueue -> Queue

ConcurrentSkipListMap -> SortedMap

ConcurrentSkipListSet -> SortedSet

#### ConcurrentHashMap
ConcurrentHashMap并不是将每个方法都在同一个锁上同步并使得每次只能有一个线程访问容器，而是使用一种粒度更细的加锁机制来实现更大程度的共享，这种机制称为分段锁。
在这种机制下，任意数量的读取线程可以并发地访问Map，执行读取操作的线程和执行写入操作的线程客户并发地访问Map，并且一定数量的写入线程可以并发地修改Map。
ConcurrentHashMap带来的好处是：在并发访问环境下降实现更高的吞吐量，而在单线程环境中值损失非常小的性能。

ConcurrentHashMap返回的迭代器具有弱一致性，而并非“及时失败”。弱一致性的迭代器可以容忍并发的修改，当创建迭代器时会遍历已有的元素，并可以（但是不保证）在迭代器被构造后将修改操作反映给容器。

一些需要在整个Map上进行计算的方法，例如size和isEmpty，这些方法的语义被略微减弱了以反映容器的并发特性。
由于size返回的结果在计算时可能已经过期，它实际上只是一个估计值，因此允许size返回一个近似值而不是一个精确值。

只有当应用程序需要加锁Map以进行独占访问时，才应该放弃使用ConcurrentHashMap。

<pre>
public interface ConcurrentMap<K,V> extends Map<K,V> {
    //仅当K没有响应的映射值才插入
    V putIfAbsent(K key,V value);

    //仅当K被映射到V时才移除
    boolean remove(K key,V value);

    //仅当K被映射到oldValue时才替换为newValue
    boolean replace(K key,V oldValue,V newValue);

    //仅当K被映射到某个值时才替换为newValue
    V replace(K key,V newValue);
}

</pre>

由于ConcurrentHashMap不能被加锁来执行独占访问，因此我们无法使用客户端加锁来创建新的原子操作。
如果你需要在现有的同步Map中添加这样的功能，那么很可能意味着应该考虑使用ConcurrentMap

#### CopyOnWriteArrayList

CopyOnWriteArrayList用于替代同步List，在某些情况下它提供了更好的并发性能，并且在迭代期间不需要对容器进行加锁或复制。类似的，CopyOnWriteArraySet的作用是替代同步Set。

“写入时复制(Copy-On-Write)”容器的线程安全型在于,只要正确发布一个事实不可变的对象，那么在访问这个对象时就不需要进一步的同步。
每次修改时，都会创建并重新发布一个新的容器副本，从而实现可变性。“写入时复制”容器的迭代器保留一个指向底层基础数组的引用，这个数组当前位置位于迭代器的起始位置，由于它不会修改，因此在对其进行同步时只需要确保数组内容的可见性。
因此多个线程可以同时对这个容器进行迭代，而不会彼此干扰或修改容器的线程相互干扰。
“写入时复制”容器返回的迭代器不会抛出ConcurrentModificationException，并且返回的元素与迭代器创建时的元素完全一致，而不必考虑修改操作所带来的影响。

每当修改容器时都会复制底层数组，这需要一定的开销，特别是当容器的规模较大时。
仅当迭代操作远远多于修改操作时，才应该使用“写入时复制”容器。
这个准则很好地描述了许多事件通知系统：在分发通知时需要迭代已注册监听器链表，并调用每一个监听器，在大多数情况下，注册和注销事件的操作远少于接收事件通知的操作。

### 阻塞队列和生产者-消费者模式

阻塞队列提供了可阻塞的put和take方法，以及支持定时的offer和poll方法。
队列可以是有界也可以是无界的，无界队列永远都不会充满，因此无界队列上的put方法永远不会阻塞。

LinkedBlockingQueue

ArrayBlockingQueue

PriorityBlockingQueue

SynchronousQueue
实际上不是一个真正的队列，因为它不会为队列中元素维护存储空间。与其他队列不同的是，它维护一组线程，这些线程在等待着把元素加入或移出队列。
由于可以直接交付工作，从而降低了将数据从生产者移动到消费者的延迟。
SynchronousQueue没有存储功能，因此put和take会一直阻塞，直到有另一个线程已经准备好参与到交付过程中，仅当有足够的消费者，并且总是有一个消费者准备好获取交付的工作时，才适合使用同步队列。

#### 示例：桌面搜索

<pre>
public class ProducerConsumer {
    static class FileCrawler implements Runnable {
        private final BlockingQueue<File> fileQueue;
        private final FileFilter fileFilter;
        private final File root;

        public FileCrawler(BlockingQueue<File> fileQueue,
                           final FileFilter fileFilter,
                           File root) {
            this.fileQueue = fileQueue;
            this.root = root;
            this.fileFilter = new FileFilter() {
                public boolean accept(File f) {
                    return f.isDirectory() || fileFilter.accept(f);
                }
            };
        }

        private boolean alreadyIndexed(File f) {
            return false;
        }

        public void run() {
            try {
                crawl(root);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void crawl(File root) throws InterruptedException {
            File[] entries = root.listFiles(fileFilter);
            if (entries != null) {
                for (File entry : entries)
                    if (entry.isDirectory())
                        crawl(entry);
                    else if (!alreadyIndexed(entry))
                        fileQueue.put(entry);
            }
        }
    }

    static class Indexer implements Runnable {
        private final BlockingQueue<File> queue;

        public Indexer(BlockingQueue<File> queue) {
            this.queue = queue;
        }

        public void run() {
            try {
                while (true)
                    indexFile(queue.take());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public void indexFile(File file) {
            // Index the file...
        };
    }

    private static final int BOUND = 10;
    private static final int N_CONSUMERS = Runtime.getRuntime().availableProcessors();

    public static void startIndexing(File[] roots) {
        BlockingQueue<File> queue = new LinkedBlockingQueue<File>(BOUND);
        FileFilter filter = new FileFilter() {
            public boolean accept(File file) {
                return true;
            }
        };

        for (File root : roots)
            new Thread(new FileCrawler(queue, filter, root)).start();

        for (int i = 0; i &lt; N_CONSUMERS; i++)
            new Thread(new Indexer(queue)).start();
    }
}
</pre>

#### 串行线程封闭

#### 双端队列与工作密取

Deque、BlockingDeque

ArrayDeque LinkedBlockingDeque

正如阻塞队列适用于生产者-消费者模式，双端队列适用于工作密取（work stealing）。在工作密取的设计中，每个消费者都有各自的双端队列，如果一个消费者完成了自己双端队列中的全部工作，那么它可以从其它消费者的双端队列的末尾秘密地获取工作。密取工作模式比传统的生产者-消费者具有更高的可伸缩性。因为工作线程不会再单个共享的任务队列上发生竞争。

当一个工作线程找到新的任务单元时，它会将其放到自己队列的末尾（或者在工作共享设计模式中，放入其他工作者线程的队列中）。当双端队列为空时，它会在另一个线程的队列队尾查找新任务，从而确保每个线程都保持忙碌状态。

### 阻塞方法和中断方法

线程阻塞或暂停执行的原因：等待I/O操作结束，等待获得一个锁，等待从Thread.sleep方法中醒来，等来另一个线程的计算结果。
当线程阻塞时，它通常被挂起，并处于某种阻塞状态(BLOCKED、WAITING或TIMED_WAITING)

如果某方法抛出InterruptedException时，表示该方法是一个阻塞方法，如果这个方法被中断，那么它将努力提前结束阻塞状态。

中断是一种协作机制。一个线程不能强制其他线程停止正在执行的操作而去执行其他的操作。
当线程A中断B时，A仅仅是要求B在执行到某个可以暂停的地方停止正在执行的操作——前提是如果线程B愿意停下来。

最常使用中断的情况就是取消某个操作。

当代码中调用了一个将抛出InterruptedException异常的方法时，你自己的方法也就变成了一个阻塞方法，并且必须要处理对中断的响应。对于库代码来说，有两种基本选择：

**传递InterruptedException** 传递InterruptedException的方法包括：根本不捕获该异常，或者捕获该异常，如何再执行某种简单的清理工作后再次抛出这个异常。

**恢复中断** 有时候不能抛出InterruptedException，在这些情况下，必须捕获InterruptedException，并通过调用当前线程上的interrupt方法恢复中断状态，这样在调用栈中更高层的代码将看到引发了一个中断。

<pre>
public class TaskRunnable implements Runnable {
    BlockingQueue<Task> queue;

    public void run() {
        try {
            processTask(queue.take());
        } catch (InterruptedException e) {
            // restore interrupted status
            Thread.currentThread().interrupt();
        }
    }

    void processTask(Task task) {
        // Handle the task
    }

    interface Task {
    }
}
</pre>

*中断示例*

<pre>
public class FileClock implements Runnable {

	@Override
    public void run() {
		for (int i = 0; i &lt; 10; i++) {
			System.out.printf("%s\n", new Date());
			try {
				// Sleep during one second
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
                System.out.println("after throw InterruptedException the interrupt flag : " + Thread.currentThread().isInterrupted());
				System.out.println("The FileClock has been interrupted");
			}
		}
	}
}
</pre>

<pre>
        // Creates a FileClock runnable object and a Thread
        // to run it
        FileClock clock=new FileClock();
        Thread thread=new Thread(clock);

        // Starts the Thread
        thread.start();
        try {
            // Waits five seconds
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        };
        // Interrupts the Thread
        thread.interrupt();
</pre>

输出：

    Fri Dec 05 16:35:19 CST 2014
    Fri Dec 05 16:35:20 CST 2014
    Fri Dec 05 16:35:21 CST 2014
    Fri Dec 05 16:35:22 CST 2014
    Fri Dec 05 16:35:23 CST 2014
    after throw InterruptedException the interrupt flag : false
    The FileClock has been interrupted
    Fri Dec 05 16:35:24 CST 2014
    Fri Dec 05 16:35:25 CST 2014
    Fri Dec 05 16:35:26 CST 2014
    Fri Dec 05 16:35:27 CST 2014
    Fri Dec 05 16:35:28 CST 2014
    

线程的thread.interrupt()方法是中断线程，将会设置该线程的中断状态位，即设置为true，中断的结果线程是死亡、还是等待新的任务或是继续运行至下一步，就取决于这个程序本身。线程会不时地检测这个中断标示位，以判断线程是否应该被中断（中断标示值是否为true）。它并不像stop方法那样会中断一个正在运行的线程。

如果一个线程处于了阻塞状态（如线程调用了thread.sleep、thread.join、thread.wait、1.5中的condition.await、以及可中断的通道上的 I/O 操作方法后可进入阻塞状态），
则在线程在检查中断标示时如果发现中断标示为true，则会在这些阻塞方法（sleep、join、wait、1.5中的condition.await及可中断的通道上的 I/O 操作方法）调用处抛出InterruptedException异常，
**并且在抛出异常后立即将线程的中断标示位清除，即重新设置为false**。抛出异常是为了线程从阻塞状态醒过来，并在结束线程前让程序员有足够的时间来处理中断请求。

<pre>
public class FileClock2 implements Runnable {

	@Override
    public void run() {
		for (int i = 0; i &lt; 10; i++) {
			System.out.printf("%s\n", new Date());
			try {
				// Sleep during one second
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
                System.out.println("after throw InterruptedException the interrupt flag : " + Thread.currentThread().isInterrupted());
                System.out.println("The FileClock has been interrupted");
                Thread.currentThread().interrupt();
                System.out.println("after recovery interrupt the interrupt flag : " + Thread.currentThread().isInterrupted());
			}
		}
	}
}
</pre>

输出

    Fri Dec 05 16:39:48 CST 2014
    Fri Dec 05 16:39:49 CST 2014
    Fri Dec 05 16:39:50 CST 2014
    Fri Dec 05 16:39:51 CST 2014
    Fri Dec 05 16:39:52 CST 2014
    after throw InterruptedException the interrupt flag : false
    The FileClock has been interrupted
    after recovery interrupt the interrupt flag : true
    Fri Dec 05 16:39:53 CST 2014
    after throw InterruptedException the interrupt flag : false
    The FileClock has been interrupted
    after recovery interrupt the interrupt flag : true
    Fri Dec 05 16:39:53 CST 2014
    after throw InterruptedException the interrupt flag : false
    The FileClock has been interrupted
    after recovery interrupt the interrupt flag : true
    Fri Dec 05 16:39:53 CST 2014
    after throw InterruptedException the interrupt flag : false
    The FileClock has been interrupted
    after recovery interrupt the interrupt flag : true
    Fri Dec 05 16:39:53 CST 2014
    after throw InterruptedException the interrupt flag : false
    The FileClock has been interrupted
    after recovery interrupt the interrupt flag : true
    Fri Dec 05 16:39:53 CST 2014
    after throw InterruptedException the interrupt flag : false
    The FileClock has been interrupted
    after recovery interrupt the interrupt flag : true
    
每次在捕获到InterruptedException后，都使用Thread.currentThread().interrupt()恢复中断，所以每次调用TimeUnit.SECONDS.sleep(1)都会检测到中断并抛出InterruptedException
    
<pre>
public class FileClock3 implements Runnable {

	@Override
    public void run() {
		for (int i = 0; i &lt; 10; i++) {
			System.out.printf("%s\n", new Date());
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Thread interrupted\n Exiting...");
                break;
            }
			try {
				// Sleep during one second
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
                System.out.println("after throw InterruptedException the interrupt flag : " + Thread.currentThread().isInterrupted());
                System.out.println("The FileClock has been interrupted");
                Thread.currentThread().interrupt();
                System.out.println("after recovery interrupt the interrupt flag : " + Thread.currentThread().isInterrupted());
			}
		}
	}
}
</pre>    

输出：

    Fri Dec 05 16:44:44 CST 2014
    Fri Dec 05 16:44:45 CST 2014
    Fri Dec 05 16:44:46 CST 2014
    Fri Dec 05 16:44:47 CST 2014
    Fri Dec 05 16:44:48 CST 2014
    after throw InterruptedException the interrupt flag : false
    The FileClock has been interrupted
    after recovery interrupt the interrupt flag : true
    Fri Dec 05 16:44:49 CST 2014
    Thread interrupted
     Exiting...
     
### 同步工具类
     
#### 闭锁     
闭锁作用相当于一扇门：在闭锁到达某一状态之前，这扇门一直是关闭的，所有的线程都会在这扇门前等待（阻塞）。只有门打开后，所有的线程才会同时继续运行。
当闭锁到达结束位置后，将不会再改变状态，因此这扇门将永远保持打开状态。

闭锁可以用来确保某些活动直到其它活动都完成后才继续执行，例如：

1.确保某个计算在其所有资源都被初始化之后才继续执行。二元闭锁（只有两个状态）可以用来表示“资源R已经被初始化”，而所有需要R操作都必须先在这个闭锁上等待。

2.确保某个服务在所有其他服务都已经启动之后才启动。这时就需要多个闭锁。让S在每个闭锁上等待，只有所有的闭锁都打开后才会继续运行。

3.等待直到某个操作的参与者（例如，多玩家游戏中的玩家）都就绪再继续执行。在这种情况下，当所有玩家都准备就绪时，闭锁将到达结束状态。

CountDownLatch 是一种灵活的闭锁实现，可以用在上述各种情况中使用。闭锁状态包含一个计数器，初始化为一个正数，表示要等待的事件数量。countDown() 方法会递减计数器，表示等待的事件中发生了一件。await() 方法则阻塞，直到计数器值变为0。

*示例*

<pre>
public class Waiter implements Runnable{

    CountDownLatch latch = null;

    public Waiter(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Waiter Released");
    }
}
</pre>

<pre>
public class Decrementer implements Runnable {

    CountDownLatch latch = null;

    public Decrementer(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {

        try {
            Thread.sleep(1000);
            this.latch.countDown();

            Thread.sleep(1000);
            this.latch.countDown();

            Thread.sleep(1000);
            this.latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
</pre>

<pre>
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);

        Waiter      waiter      = new Waiter(latch);
        Decrementer decrementer = new Decrementer(latch);

        new Thread(waiter)     .start();
        new Thread(decrementer).start();

        Thread.sleep(4000);
    }
</pre>

*在计时测试中使用闭锁来启动和停止线程*

<pre>
public class TestHarness {
    public long timeTasks(int nThreads, final Runnable task)
            throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread() {
                public void run() {
                    try {
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException ignored) {
                    }
                }
            };
            t.start();
        }

        long start = System.nanoTime();
        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();
        return end - start;
    }
}
</pre>

#### FutureTask
FutureTask也可以作为闭锁。FutureTask是通过 Callable 来实现的，相当于一种可生成结果的 Runnable ，并且可处于以下三种状态：等待运行，正在运行，运行完成。当FutureTask进入完成状态后，它会停留在这个状态上。

Future.get 用来获取计算结果，如果FutureTask还未运行完成，则会阻塞。FutureTask 将计算结果从执行计算的线程传递到获取这个结果的线程，而FutureTask 的规范确保了这种传递过程能实现结果的安全发布。

FutureTask在Executor框架中表示异步任务，还可以用来表示一些时间较长的计算，这些计算可以在使用计算结果之前启动。

<pre>
public class Preloader {
    ProductInfo loadProductInfo() throws DataLoadException {
        return null;
    }

    private final FutureTask<ProductInfo> future =
        new FutureTask<ProductInfo>(new Callable<ProductInfo>() {
            public ProductInfo call() throws DataLoadException {
                return loadProductInfo();
            }
        });
    private final Thread thread = new Thread(future);

    public void start() { thread.start(); }

    public ProductInfo get()
            throws DataLoadException, InterruptedException {
        try {
            return future.get();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof DataLoadException)
                throw (DataLoadException) cause;
            else
                throw LaunderThrowable.launderThrowable(cause);
        }
    }

    interface ProductInfo {
    }
}

class DataLoadException extends Exception { }
</pre>

Callable表示的任务可以抛出受检查的或未受检查的异常，并且任何代码都可能抛出一个Error。无论任务代码抛出什么异常，都会被封装到一个ExecutionException中，并在Future.get中被抛出。
这将使调用get的代码变得复杂，因为它不仅需要处理可能出现的ExecutionException（以及未检查的CancellationException），而且还由于ExecutionException是作为一个Throwable类返回的，因此处理起来并不容易。

*强制将未检查的Throwable转换为RuntimeException*
<pre>
public class LaunderThrowable {

    /**
     * Coerce an unchecked Throwable to a RuntimeException
     * <p/>
     * If the Throwable is an Error, throw it; if it is a
     * RuntimeException return it, otherwise throw IllegalStateException
     */
    public static RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException)
            return (RuntimeException) t;
        else if (t instanceof Error)
            throw (Error) t;
        else
            throw new IllegalStateException("Not unchecked", t);
    }
}
</pre>

#### 信号量

计数信号量（Counting Semaphore）用来控制同时访问某个特定资源的操作数量，或者同时执行某个指定操作的数量。计数信号量还可以用来实现某种资源池，或者对容器施加边界。

Semaphore中管理着一组虚拟的许可（permit），许可的初始数量可通过构造函数来指定。在执行操作时可以首先获得许可（只要还有剩余的许可），并在使用以后释放许可。如果没有许可，那么acquire将阻塞直到许可（或者被中断或者超时）。release方法将返回一个许可信号量。

二元信号量（初始值为1的Semaphore）可以用作互斥体(mutex)，并具备不可重入的加锁语义：谁拥有这个唯一的许可，谁就拥有了互斥锁。

Semaphore可以用于实现资源池。

Semaphore可以将任何一种容器变成有界阻塞容器。

*示例*

<pre>
public class PrintQueue {
    private final Semaphore semaphore = new Semaphore(1);

    public void printJob (Object document){
        try {
            semaphore.acquire();
            long duration=(long)(Math.random()*10);
            System.out.printf("%s: PrintQueue: Printing a Job during %d seconds\n",Thread.currentThread().getName(),duration);
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}
</pre>

<pre>
public class Job implements Runnable {
    private PrintQueue printQueue;

    public Job(PrintQueue printQueue) {
        this.printQueue = printQueue;
    }

    @Override
    public void run() {
        System.out.printf("%s: Going to print a job\n",Thread. currentThread().getName());
        printQueue.printJob(new Object());
        System.out.printf("%s: The document has been printed\n",Thread.currentThread().getName());
    }
}
</pre>

<pre>
    public static void main(String[] args) {
        PrintQueue printQueue=new PrintQueue();
        Thread thread[]=new Thread[10];
        for (int i=0; i<10; i++){
            thread[i]=new Thread(new Job(printQueue),"Thread"+i);
        }
        for (int i=0; i<10; i++){
            thread[i].start();
        }
    }
</pre>

*使用Semaphore为容器设计边界*

<pre>
public class BoundedHashSet <T> {
    private final Set<T> set;
    private final Semaphore sem;

    public BoundedHashSet(int bound) {
        this.set = Collections.synchronizedSet(new HashSet<T>());
        sem = new Semaphore(bound);
    }

    public boolean add(T o) throws InterruptedException {
        sem.acquire();
        boolean wasAdded = false;
        try {
            wasAdded = set.add(o);
            return wasAdded;
        } finally {
            if (!wasAdded)
                sem.release();
        }
    }

    public boolean remove(Object o) {
        boolean wasRemoved = set.remove(o);
        if (wasRemoved)
            sem.release();
        return wasRemoved;
    }
}
</pre>

#### 栅栏

栅栏（Bariier）类似于闭锁，它能阻塞一组线程知道某个事件发生。栅栏与闭锁的关键区别在于，所有的线程必须同时到达栅栏位置，才能继续执行。闭锁用于等待等待时间，而栅栏用于等待线程。
栅栏用于实现一些协议，例如几个家庭成员决定在某个地方集合：”所有人6:00在麦当劳集合，到了以后要等其他人，之后再讨论下一步要做的事”。

CyclicBarrier可以使一定数量的参与方反复地在栅栏位置汇集，它在并行迭代算法中非常有用。

- CyclicBarrier(int parties)  创建一个新的 CyclicBarrier，它将在给定数量的参与者（线程）处于等待状态时启动，但它不会在启动 barrier 时执行预定义的操作。
- CyclicBarrier(int parties, Runnable barrierAction) 创建一个新的 CyclicBarrier，它将在给定数量的参与者（线程）处于等待状态时启动，并在启动 barrier 时执行给定的屏障操作，该操作由最后一个进入 barrier 的线程执行。
  
*示例*
  
<pre>
public class CyclicBarrierRunnable implements Runnable {

    CyclicBarrier barrier1 = null;
    CyclicBarrier barrier2 = null;

    public CyclicBarrierRunnable(CyclicBarrier barrier1, CyclicBarrier barrier2) {
        this.barrier1 = barrier1;
        this.barrier2 = barrier2;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() +
                    " waiting at barrier 1");
            this.barrier1.await();

            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() +
                    " waiting at barrier 2");
            this.barrier2.await();

            System.out.println(Thread.currentThread().getName() +
                    " done!");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
</pre>

<pre>
    public static void main(String[] args) {
        Runnable barrier1Action = new Runnable() {
            public void run() {
                System.out.println("BarrierAction 1 executed ");
            }
        };
        Runnable barrier2Action = new Runnable() {
            public void run() {
                System.out.println("BarrierAction 2 executed ");
            }
        };

        CyclicBarrier barrier1 = new CyclicBarrier(2, barrier1Action);
        CyclicBarrier barrier2 = new CyclicBarrier(2, barrier2Action);

        CyclicBarrierRunnable barrierRunnable1 =
                new CyclicBarrierRunnable(barrier1, barrier2);

        CyclicBarrierRunnable barrierRunnable2 =
                new CyclicBarrierRunnable(barrier1, barrier2);

        new Thread(barrierRunnable1).start();
        new Thread(barrierRunnable2).start();

    }
</pre>

*通过CyclicBarrier协调细胞自动衍生系统中的计算*

<pre>
public class CellularAutomata {
    private final Board mainBoard;
    private final CyclicBarrier barrier;
    private final Worker[] workers;

    public CellularAutomata(Board board) {
        this.mainBoard = board;
        int count = Runtime.getRuntime().availableProcessors();
        this.barrier = new CyclicBarrier(count,
                new Runnable() {
                    public void run() {
                        mainBoard.commitNewValues();
                    }});
        this.workers = new Worker[count];
        for (int i = 0; i < count; i++)
            workers[i] = new Worker(mainBoard.getSubBoard(count, i));
    }

    private class Worker implements Runnable {
        private final Board board;

        public Worker(Board board) { this.board = board; }
        public void run() {
            while (!board.hasConverged()) {
                for (int x = 0; x < board.getMaxX(); x++)
                    for (int y = 0; y < board.getMaxY(); y++)
                        board.setNewValue(x, y, computeValue(x, y));
                try {
                    barrier.await();
                } catch (InterruptedException ex) {
                    return;
                } catch (BrokenBarrierException ex) {
                    return;
                }
            }
        }

        private int computeValue(int x, int y) {
            // Compute the new value that goes in (x,y)
            return 0;
        }
    }

    public void start() {
        for (int i = 0; i < workers.length; i++)
            new Thread(workers[i]).start();
        mainBoard.waitForConvergence();
    }

    interface Board {
        int getMaxX();
        int getMaxY();
        int getValue(int x, int y);
        int setNewValue(int x, int y, int value);
        void commitNewValues();
        boolean hasConverged();
        void waitForConvergence();
        Board getSubBoard(int numPartitions, int index);
    }
}

</pre>

##### Exchanger
Exchanger是另外一种形式的栅栏，它允许在2个线程间定义同步点，当2个线程到达这个点，他们相互交换数据类型，使用第一个线程的数据类型变成第二个的，然后第二个线程的数据类型变成第一个的。

- exchange(V x) 等待另一个线程到达此交换点（除非当前线程被中断），然后将给定的对象传送给该线程，并接收该线程的对象
- exchange(V x, long timeout, TimeUnit unit) 等待另一个线程到达此交换点（除非当前线程被中断，或者超出了指定的等待时间），然后将给定的对象传送给该线程，同时接收该线程的对象。

Exchanger是一种两方（Two-Party）栅栏，各方在栅栏位置上交换数据。当两方执行不对称的操作时，Exchanger会非常有用，例如当一个线程想缓冲区写入数据，而另一个线程从缓冲区中读取数据。
这些线程可以使用Exchanger来汇合，并将满的缓冲区与空的缓冲区交换。当两个线程通过Exchanger交换对象时，这种交换就把这两个对象安全地发布给了另一方。

数据交换的时机取决于应用程序的响应需求。最简单的方案是：当缓冲区被填满时，由填充任务进行交换，当缓冲区为空时，由清空任务进行交换。
这样会把需要交换的次数将到最低，但如果新数据的到达率不可预测，那么一些数据的处理过程就将延迟。另一个方法是，不仅当缓冲被填满时进行交换，并且当缓冲被填充到一定程度并保持一定时间后也进行交换。

<pre>
public class ExchangerRunnable implements Runnable {

    private Exchanger<Object> exchanger;
    Object    object    = null;

    public ExchangerRunnable(Exchanger<Object> exchanger, Object object) {
        this.exchanger = exchanger;
        this.object = object;
    }

    @Override
    public void run() {
        Object previous = this.object;
        try {
            this.object = this.exchanger.exchange(this.object);
            System.out.println(
                    Thread.currentThread().getName() +
                            " exchanged " + previous + " for " + this.object);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
</pre>

<pre>
public static void main(String[] args) {
    Exchanger exchanger = new Exchanger();

    ExchangerRunnable exchangerRunnable1 =
            new ExchangerRunnable(exchanger, "A");

    ExchangerRunnable exchangerRunnable2 =
            new ExchangerRunnable(exchanger, "B");

    new Thread(exchangerRunnable1).start();
    new Thread(exchangerRunnable2).start();
}
</pre>

### 构建高效且可伸缩的结果缓存

*使用HashMap和同步机制来初始化缓存*

<pre>
public class Memoizer1 <A, V> implements Computable<A, V> {
    @GuardedBy("this") private final Map<A, V> cache = new HashMap<A, V>();
    private final Computable<A, V> c;

    public Memoizer1(Computable<A, V> c) {
        this.c = c;
    }

    public synchronized V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}


interface Computable <A, V> {
    V compute(A arg) throws InterruptedException;
}

class ExpensiveFunction
        implements Computable<String, BigInteger> {
    public BigInteger compute(String arg) {
        // after deep thought...
        return new BigInteger(arg);
    }
}
</pre>

Memoizer1能够确保线程安全性，但会带来一个明显的可伸缩性问题：每次只能有一个线程执行compute。

*使用ConcurrentHashMap替换HashMap*

<pre>
public class Memoizer2 <A, V> implements Computable<A, V> {
    private final Map<A, V> cache = new ConcurrentHashMap<A, V>();
    private final Computable<A, V> c;

    public Memoizer2(Computable<A, V> c) {
        this.c = c;
    }

    public V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
</pre>

Memoizer2比Memoizer1有着更好的并发行为：多线程可以并发使用它。但是它在作为缓存时仍然存在一些不足——当两个线程同时调用compute时存在一个漏洞，可能会导致计算得到相同的值。
使用memoization的情况下，这只会带来低效，因为缓存的作用是避免相同的数据被计算多次。

Memoizer2的问题在于：如果某个线程启动了一个开销很大的计算，而其他线程并不知道这个计算正在进行，那么很可能会重复这个计算。

*使用FutureTask的Memoizing封装器*

<pre>
public class Memoizer3<A, V> implements Computable<A, V> {

	private final Map<A, FutureTask<V>> cache = new ConcurrentHashMap<A, FutureTask<V>>();

	private final Computable<A, V> c;

	public Memoizer3(Computable<A, V> c) {
        this.c = c;
	}

	@Override
	public V compute(final A arg) throws InterruptedException {
		FutureTask<V> f = cache.get(arg);
		if (f == null) {
			Callable<V> eval = new Callable<V>() {

				@Override
				public V call() throws Exception {
					return c.compute(arg);
				}
			};
			FutureTask<V> ft = new FutureTask<V>(eval);
			f = ft;
			cache.put(arg, ft);
			ft.run();// 调用c.compute发生在这里
		}
		try {
			return f.get();
		} catch (ExecutionException e) {
			throw LaunderThrowable.launderThrowable(e.getCause());
		}
	}

}
</pre>

Memoizer3表现出了非常好的并发性（基本上是源于ConcurrentHashMap高效的并发性），若结果已经计算出来，那么将立即返回。
如果其他线程正在计算该结果，那么新到的线程将一直等待这个结果被计算出来。
它只有一个缺陷，即仍然存在两个线程计算出相同值的漏洞。这个漏洞的发生概率要远小于Memoizer2中发生的概率。
由于compute方法中的if代码块仍然是非原子的“先检查在执行”操作，因此两个线程仍然可能在同一时间内调用compute来计算相同的值，即二者都没有在缓存中找到期望的值，因此都开始计算。

*使用ConcurrentHashMap解决“若没有则添加”的漏洞*

<pre>
public class Memoizer <A, V> implements Computable<A, V> {
    private final ConcurrentMap<A, Future<V>> cache
            = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A, V> c;

    public Memoizer(Computable<A, V> c) {
        this.c = c;
    }

    public V compute(final A arg) throws InterruptedException {
        while (true) {
            Future<V> f = cache.get(arg);
            if (f == null) {
                Callable<V> eval = new Callable<V>() {
                    public V call() throws InterruptedException {
                        return c.compute(arg);
                    }
                };
                FutureTask<V> ft = new FutureTask<V>(eval);
                //只有在不存在缓存时才会将ft加入到map中
                f = cache.putIfAbsent(arg, ft);
                if (f == null) {
                    f = ft;
                    ft.run();
                }
            }
            try {
                return f.get();
            } catch (CancellationException e) {
                cache.remove(arg, f);
            } catch (ExecutionException e) {
                throw LaunderThrowable.launderThrowable(e.getCause());
            }
        }
    }
}
</pre>

当缓存的是Future而不是值时，将导致缓存污染问题：如果某个计算被取消或者失败，那么在计算的这个结果时将指明计算过程被取消或者失败。
为了避免这种情况，如果Memoizer发现计算被取消，那么将把Future从缓存中移除。如果检测到RuntimeException，那么也会移除Future。
Memoizer同样没有解决缓存逾期的问题，但它可以通过FutureTask的子类来解决。