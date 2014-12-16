性能可以通过多个方面来衡量，包括

 - 吞吐量: 指一组并发任务中已完成任务所占的比例
 - 响应性：指请求从发出到完成之间的时间（也称为延迟）
 - 可伸缩性：只在增加更多资源的情况下（通常指CPU），吞吐量（或者缓解短缺）的提升情况
 
### 正确性测试
在为某个并发类设计单元测试时，首先需要执行与测试串行类时相同的分析——找出需要检查的不变性条件和后验条件。
 
*基于信号量的有界缓存*

<pre>
public class BoundedBuffer<E> {
    private final Semaphore availableItems;
    private final Semaphore availableSpaces;

    @GuardedBy("this")
    private final E[] items;

    @GuardedBy("this")
    private int putPoisition = 0;
    @GuardedBy("this")
    private int takePoisition = 0;

    public BoundedBuffer(int capacity) {
        availableItems = new Semaphore(0);
        availableSpaces = new Semaphore(capacity);
        items = (E[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return availableItems.availablePermits() == 0;
    }

    public boolean isFull() {
        return availableSpaces.availablePermits() == 0;
    }

    public void put(E x) throws InterruptedException {
        availableSpaces.acquire();
        doInsert(x);
        availableItems.release();
    }

    public E take() throws InterruptedException {
        availableItems.acquire();
        E item = doExtract();
        availableSpaces.release();
        return item;
    }

    private E doExtract() {
        int i = takePoisition;
        E x = items[i];
        items[i] = null;
        takePoisition = (++i == items.length) ? 0 : i;
        return x;
    }

    private synchronized void doInsert(E x) {
        int i = putPoisition;
        items[i] = x;
        putPoisition = (++i == items.length) ? 0 : i;
    }
}
</pre>

*基本的单元测试*

<pre>
public class BoundedBufferTest {

    @Test
    public void testIsEmptyWhenConstructed() {
        BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        Assert.assertTrue(bb.isEmpty());
        Assert.assertFalse(bb.isFull());
    }

    @Test
    public void testIsFullAfterPuts() throws InterruptedException {
        BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        for (int i = 0; i < 10; i ++) {
            bb.put(i);
        }
        Assert.assertFalse(bb.isEmpty());
        Assert.assertTrue(bb.isFull());
    }
}
</pre>

#### 对阻塞操作的测试
在测试并发的基本属性时，需要引入多个线程。

<pre>
    @Test
    public void testTakeBlocksWhenEmpty() {
        final BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        Thread taker = new Thread() {
            @Override
            public void run() {
                try {
                    int unused = bb.take();
                    Assert.fail();
                } catch (InterruptedException e) {
                }
            }
        };
        try {
            taker.start();
            Thread.sleep(1000);
            taker.interrupt();
            taker.join(1000);
            Assert.assertFalse(taker.isAlive());
        } catch (InterruptedException e) {
            Assert.fail();
        }
    }
</pre>

如果某方法需要在某个特定条件下阻塞，那么当测试这种行为时，只有当线程不再继续执行时，测试才是成功的。
要测试一个方法的阻塞行为，类似于测试一个抛出异常的方法：如果这个方法可以正常返回，那么就意味着测试失败。

当方法被成功阻塞后，还必须使用方法解除阻塞。

#### 安全性测试
要测试在生产者——消费者模式中使用的类，一种有效的方法就是检查被放入队列中和从队列中取出的各个元素。
这种方法的一个简单实现是，当元素被插入到队列时，同时将其插入到一个“影子”列表，当从队列中删除该元素时，同时也从“影子”列表中删除，任何在测试程序运行完以后判断“影子”列表是否为空。然而这种方法可能会干扰测试线程的调度，因为在修改“影子”列表时需要同步，并可能会阻塞。

一种更好的方法是，通过一个对顺序敏感的校验和计算函数来计算 所有入列的元素以及出列元素的检验和，并进行比较。如果二者相等，那么测试就是成功的。如果只有一个生产者一个消费者，那么 这种方法能发挥最大的作用。因为它不仅能测试出是否取出了正确的元素，而且还能测试出元素被取出的顺序是否正确、
         
如果要将这种方法扩展到多生产者多消费者的情况时，就需要一个对元素入列和出列顺序不敏感的校验和函数。从而在测试程序运行完以后，可以将多个检验和以不同的顺序组合起来，如果不是这样，多个线程就需要访问 同一个共享的检验和变量 ，因此就需要同步，这将成为一个并发的瓶颈。

要确保测试程序能够正确地测试所有要点，就一定不能让编译器可以预先猜测到检验和的值。

由于大多数随机类生成器都是线程安全的。并且会带来额外的同步开销。所以还不如用一个简单的伪随机函数 。

*适合在测试中使用的随机数生成器*

<pre>
public class PutTakeTest {

    protected static final ExecutorService pool = Executors.newCachedThreadPool();
    protected CyclicBarrier barrier;
    protected final BoundedBuffer<Integer> bb;
    protected final int nTrials, nPairs;
    protected final AtomicInteger putSum = new AtomicInteger(0);
    protected final AtomicInteger takeSum = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        new PutTakeTest(10, 10, 100000).test(); // sample parameters
        pool.shutdown();
    }

    public PutTakeTest(int capacity, int npairs, int ntrials) {
        this.bb = new BoundedBuffer<Integer>(capacity);
        this.nTrials = ntrials;
        this.nPairs = npairs;
        this.barrier = new CyclicBarrier(npairs * 2 + 1);
    }

    void test() {
        try {
            for (int i = 0; i < nPairs; i++) {
                pool.execute(new Producer());
                pool.execute(new Consumer());
            }
            barrier.await(); // wait for all threads to be ready
            barrier.await(); // wait for all threads to finish
            Assert.assertEquals(putSum.get(), takeSum.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static int xorShift(int y) {
        y ^= (y << 6);
        y ^= (y >>> 21);
        y ^= (y << 7);
        return y;
    }

    class Producer implements Runnable {
        public void run() {
            try {
                int seed = (this.hashCode() ^ (int) System.nanoTime());
                int sum = 0;
                barrier.await();
                for (int i = nTrials; i > 0; --i) {
                    bb.put(seed);
                    sum += seed;
                    seed = xorShift(seed);
                }
                putSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    class Consumer implements Runnable {
        public void run() {
            try {
                barrier.await();
                int sum = 0;
                for (int i = nTrials; i > 0; --i) {
                    sum += bb.take();
                }
                takeSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
</pre>

#### 资源管理的测试

#### 使用回调
通过使用自定义的线程工厂，可以对线程的创建过程进行控制。

*线程工厂类*

<pre>
public class TestingThreadFactory implements ThreadFactory {

    public final AtomicInteger numCreated = new AtomicInteger();
    private final ThreadFactory factory = Executors.defaultThreadFactory();

    @Override
    public Thread newThread(Runnable r) {
        numCreated.incrementAndGet();
        return factory.newThread(r);
    }
}
</pre>

*验证线程池扩展能力的测试方法*

<pre>
public class TestThreadPool {

    private final TestingThreadFactory threadFactory = new TestingThreadFactory();

    @Test
    public void testPoolExpansion() throws InterruptedException {
        int MAX_SIZE = 10;
        ExecutorService exec = Executors.newFixedThreadPool(MAX_SIZE, threadFactory);

        for (int i = 0; i < 10 * MAX_SIZE; i++)
            exec.execute(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(Long.MAX_VALUE);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        for (int i = 0;
             i < 20 && threadFactory.numCreated.get() < MAX_SIZE;
             i++)
            Thread.sleep(100);
        Assert.assertEquals(threadFactory.numCreated.get(), MAX_SIZE);
        exec.shutdownNow();
    }
}
</pre>

#### 产生更多的交替操作
由于并发代码的大多数错误都是一些低概率事件，因此在测试并发错误时需要返回地执行许多次，但有些方法可以提高发现这些错误的概率。

有一种有用的方法可以提高交替操作的数量，以便能更有效地搜索程序的状态空间：在访问共享状态的操作中，使用Thread.yield将产生更多的上下文切换。

### 性能测试
#### 在PutTakeTest中增加计时功能

<pre>
public class BarrierTimer implements Runnable {
    private boolean started;
    private long startTime, endTime;

    public synchronized void run() {
        long t = System.nanoTime();
        if (!started) {
            started = true;
            startTime = t;
        } else
            endTime = t;
    }

    public synchronized void clear() {
        started = false;
    }

    public synchronized long getTime() {
        return endTime - startTime;
    }
}
</pre>

<pre>
public class TimedPutTakeTest extends PutTakeTest {
    private BarrierTimer timer = new BarrierTimer();

    public TimedPutTakeTest(int cap, int pairs, int trials) {
        super(cap, pairs, trials);
        barrier = new CyclicBarrier(nPairs * 2 + 1, timer);
    }

    public void test() {
        try {
            timer.clear();
            for (int i = 0; i < nPairs; i++) {
                pool.execute(new PutTakeTest.Producer());
                pool.execute(new PutTakeTest.Consumer());
            }
            barrier.await();
            barrier.await();
            long nsPerItem = timer.getTime() / (nPairs * (long) nTrials);
            System.out.print("Throughput: " + nsPerItem + " ns/item");
            Assert.assertEquals(putSum.get(), takeSum.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        int tpt = 100000; // trials per thread
        for (int cap = 1; cap <= 1000; cap *= 10) {
            System.out.println("Capacity: " + cap);
            for (int pairs = 1; pairs <= 128; pairs *= 2) {
                TimedPutTakeTest t = new TimedPutTakeTest(cap, pairs, tpt);
                System.out.print("Pairs: " + pairs + "\t");
                t.test();
                System.out.print("\t");
                Thread.sleep(1000);
                t.test();
                System.out.println();
                Thread.sleep(1000);
            }
        }
        PutTakeTest.pool.shutdown();
    }
}
</pre>

#### 响应性衡量

### 避免性能测试的陷阱
#### 垃圾回收
有两种策略可以防止垃圾回收操作对测试结果产生偏差

1.确保垃圾回收操作在测试运行的整个期间都不会执行(调用JVM时指定-verbose:gc来判断是否执行了垃圾回收操作)
2.确保垃圾回收操作在测试期间执行多次，这样测试程序就能充分反映出运行期间的内存分配与垃圾回收等开销。

通常第二种策略更好，它要求更长的测试时间，并且更有可能反映实际环境下的性能。

#### 动态编译
当某个类第一次被加载时，JVM会通过解释字节码的方式执行它。在某个时刻，如果一个方法运行的次数足够多，那么动态编译器会将它编译为机器代码，当编译完成后，代码的执行方式将从解释执行变成直接执行。

在运行程序时使用命令行选项-xx:+PrintCompilation，当动态编译运行时将输出一条信息。

#### 对代码路径的不真实采样

#### 不真实的竞争程度

#### 无用代码的消除