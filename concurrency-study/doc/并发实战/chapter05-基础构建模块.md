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
		for (int i = 0; i &lt 10; i++) {
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

        for (int i = 0; i &lt N_CONSUMERS; i++)
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
		for (int i = 0; i < 10; i++) {
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
		for (int i = 0; i < 10; i++) {
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
		for (int i = 0; i < 10; i++) {
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