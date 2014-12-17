### 状态依赖性的管理

*有界缓存的基类*

<pre>
public class BaseBoundedBuffer<V> {
    private final V[] buf;
    private int tail;
    private int head;
    private int count;

    protected BaseBoundedBuffer(int capacity) {
        this.buf = (V[]) new Object[capacity];
    }

    protected synchronized final void doPut(V v) {
        buf[tail] = v;
        if (++ tail == buf.length) {
            tail = 0;
        }
        ++ count;
    }

    protected synchronized final V doTake() {
        V v = buf[head];
        buf[head] = null;
        if (++ head == buf.length) {
            head = 0;
        }
        -- count;
        return v;
    }

    public synchronized final boolean isFull() {
        return count == buf.length;
    }

    public synchronized final boolean isEmpty() {
        return count == 0;
    }

}
</pre>

#### 将前提条件的失败传递给调用者

<pre>
public class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V> {
    protected GrumpyBoundedBuffer(int capacity) {
        super(capacity);
    }

    public synchronized void put(V v) {
        if (isFull()) {
            throw new BufferFullException();
        }
        doPut(v);
    }

    public synchronized V take() {
        if (isEmpty()) {
            throw new BufferEmptyException();
        }
        return doTake();
    }
}

class BufferFullException extends RuntimeException {
}

class BufferEmptyException extends RuntimeException {
}
</pre>

异常应该用于发生异常条件的情况中。“缓存已满”并不是有界缓存的一个异常条件，就像“红灯”并不表示交通信号灯出现了异常。

*调用GrumpyBoundedBuffer*

<pre>
public class ExampleUsage {
    private GrumpyBoundedBuffer<String> buffer;
    int SLEEP_GRANULARITY = 50;

    void useBuffer() throws InterruptedException {
        while (true) {
            try {
                String item = buffer.take();
                // use item
                break;
            } catch (BufferEmptyException e) {
                Thread.sleep(SLEEP_GRANULARITY);
            }
        }
    }
}
</pre>

调用者可以不筋肉休眠状态，而直接重修调用take方法，这种方法被称为忙等待或自旋等待。
如果缓存的状态在很长一段时间内都不会发生变化，那么使用这种方法就会消耗大量的CPU时间。
但是，调用者可以进入休眠状态来避免消耗过多的CPU时间，但如果缓存的状态在刚调用完sleep就立即发生变化，那么将不必要地休眠一段时间。
因此客户代码避免要在二者之间进行选择：要么容忍自旋导致的CPU时钟周期浪费，要么容忍由于休眠而导致的低响应性。
除了忙等待与休眠之外，还有一种选择就是调用Thread.yield，这相当于给调度器一个提示：现在需要让出一定的时间使另一个线程运行。假如正在等待另一个线程执行工作，那么如果选择让出处理器而不是消耗完整个CPU调度时间片，可以使整体的执行过程变快。

#### 通过轮询和休眠来实现阻塞
*使用简单的阻塞实现的有界缓存*

<pre>
public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer<V> {
    private static final int SLEEP_GRANULARITY = 50;

    protected SleepyBoundedBuffer(int capacity) {
        super(capacity);
    }

    public void put(V v) throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isFull()) {
                    doPut(v);
                    return;
                }
            }
            Thread.sleep(SLEEP_GRANULARITY);
        }
    }

    public V take() throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isEmpty()) {
                    return doTake();
                }
            }
            Thread.sleep(SLEEP_GRANULARITY);
        }
    }
}
</pre>

从调用者的角度看，这种方式能很好地运行，如果某个操作可以执行，那么就立即执行，否则就阻塞，调用者无须处理失败和重试。
要选择合适的休眠时间间隔，就需要在响应性与CPU使用率之间进行权衡。休眠的间隔越小，响应性就越高，但消耗的CPU资源也越高。

#### 条件队列
条件队列：它使得一组线程（称之为等待线程集合）能够通过某种方式来等待特定的条件变成真。

正如每个java对象都可以作为一个锁，每个对象同样可以作为一个条件队列，并且Object中的wait、notify和notifyAll方法就构成了内部条件队列的API.
对象的内置锁和其内部条件是相互关联的，要调用对象X中条件队列的任何一个方法，必须持有对象X上的锁。
这是因为“等待自由状态构成的条件”与“维护状态一致性”这两种机制必须紧密地绑定在一起：只有能对状态进行检查时，才能在某个条件上等待，并且只有能修改状态时，才能从条件等待中释放另一个线程。

Object.wait会自动释放锁，并请求操作系统挂起当前线程，从而是其他线程能够获得这个锁并修改对象的状态。
当被挂起的线程醒来时，它将在返回之前重新获取锁。从直观来理解，调用wait意味着“我要去休息了，但当发生特定的事情时唤醒我”，而调用通知方法就意味着“特定的事情发生了”。

*使用条件队列实现的有界缓存*

<pre>
public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {
    public BoundedBuffer(int capacity) {
        super(capacity);
    }

    public synchronized void put(V v) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        doPut(v);
        notifyAll();
    }

    public synchronized V take() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        V v = doTake();
        notifyAll();
        return v;
    }
}
</pre>

与使用“休眠”的有界缓存相比，条件队列并没有改变原来的语义。它只是在多个方面进行了优化：CPU效率、上下文切换开销和响应性。
如果某个功能无法通过“轮询和休眠”来实现，那么使用条件队列也无法实现，但条件队列使得在表达和管理状态依赖性时更加简单和有效。

在产品的正式版本中还应包括限时版本的put和take，这样当阻塞操作不能再预计时间内完成时，可以因超时而返回。通过使用定时版本的Object.wait，可以很容易实现这个方法。

### 使用条件队列
#### 条件谓词
要想正确地使用条件队列，关键是找出对象在哪个条件谓词上等待。条件谓词是使某个操作成为状态依赖操作的前提条件。
在有界缓存中，只有当缓存不为空时，take方法才能执行，否则就必须等待。对于take方法来说，它的条件谓词就是“缓存不为空”，take方法在执行之前必须首先测试该条件谓词。
同样，put方法的条件谓词是“缓存不满”。条件谓词是由类中各个状态变量构成的表达式。

*将与条件队列相关联的条件谓词以及在这些条件谓词上等待的操作都写入文档*

#### 过早唤醒
wait方法的返回并不一定意味着线程正在等待的条件谓词已经变成真了。

内置条件队列可以与多个条件谓词一起使用。当一个线程由于调用notifyAll而醒来时，并不意味着该线程正在等待的条件谓词已经变成真了。
另外，wait方法还可以“假装”返回，而不是由于某个线程调用了notify。

在发出通知的线程调用notifyAll时，条件谓词可能已经变成真，但在重新获得锁时将再次变为假。
在线程被唤醒到wait重新获取锁的这段时间里，可能有其他线程已经获取了锁，并修改了对象的状态。
或者，条件谓词从调用wait其根本就没有变成真。你并不知道另一个线程为什么调用notify或notifyAll，也许是因为与同一条件队列相关的另一个条件谓词变成了真。

基于所有的这些原因，没当线程从wait中唤醒时，都必须再次测试条件谓词，如果条件谓词不为真，那么就继续等待（或者失败）。
由于线程在条件谓词不为真的情况下也可以反复地醒来，因此必须在一个循环中调用wait，并在每次迭代中都测试条件谓词。

#### 丢失的信号
丢失的信号是指：线程必须等待一个已经为真的条件，但是在开始等待之前没有检查条件谓词。现在，线程将等待一个已经发过的事件。
如果线程A通知了一个条件队列，而线程B随后在这个条件队列上等待，那么线程B将不会理解醒来，而是需要另一个通知来唤醒它。

#### 通知
每当在等待一个条件时，一定要确保在条件谓词变为真时通过某种方式发出通知。

在调用notify时，JVM会从这个条件队列上等待的多个线程中选择一个来唤醒，而调用notifyAll则会唤醒所有在这个条件队列上等待的线程。

由于多个线程可以基于不同的条件谓词在同一个条件队列上等待，因此如果使用notify而不是notifyAll，那么将是一种危险的操作，因为单一的通知应该尽快地释放锁，从而确保正在等待的线程尽可能地解除阻塞。

BoundedBuffer的put和take方法中采用的通知机制是保守的；每当将一个对象放入缓存或者从缓存中移走一个对象时，就执行一次通知。
我们可以对其进行优化：首先，仅当缓存从空变成非空，或者从满转为非满时，才需要释放一个线程。并且，当且仅当put或take影响到这些状态转换时，才发出通知。这也被称为“条件通知”

*条件通知*

<pre>
public class BoundedBuffer2<V> extends BaseBoundedBuffer<V> {
    public BoundedBuffer2(int capacity) {
        super(capacity);
    }

    public synchronized void put(V v) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        boolean wasEmpty = isEmpty();
        doPut(v);
        if (wasEmpty) {
            notifyAll();
        }
    }

    public synchronized V take() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        boolean wasFull = isFull();
        V v = doTake();
        if (wasFull) {
            notifyAll();
        }
        return v;
    }
}
</pre>

#### 阀门类
通过使用条件等待，可以很容易地开发一个可重新关闭的ThreadGate类

<pre>
public class ThreadGate {

    private boolean isOpen;
    private int generation;

    public synchronized void close() {
        isOpen = false;
    }

    public synchronized  void open() {
        ++generation;
        isOpen = true;
        notifyAll();
    }

    public synchronized void await() throws InterruptedException {
        int arrivalGeneration = generation;
        while (!isOpen && arrivalGeneration == generation) {
            wait();
        }
    }
}
</pre>

#### 子类的安全问题

#### 封装条件队列

#### 入口协议与出口协议

AbstractQueuedSynchronizer

### 显式的Contidion对象
对于每个Lock，可以有任意数量的Condition对象。Condition对象继承了相关的Lock对象的公平性。

<pre>
public class ConditionBoundedBuffer<T> {
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private static final int BUFFER_SIZE = 100;
    private final T[] items = (T[]) new Object[BUFFER_SIZE];
    private int tail;
    private int head;
    private int count;

    public void put(T x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[tail] = x;
            if (++ tail == items.length) {
                tail = 0;
            }
            ++ count;
            notEmpty.notifyAll();
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            T x = items[head];
            items[head] = null;
            if (++ head == items.length) {
                head = 0;
            }
            -- count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }
}
</pre>

### Synchronizer

*使用Lock来实现信号量*

<pre>
public class SemaphoreOnLock {
    private final Lock lock = new ReentrantLock();
    private  final Condition permitsAvailable = lock.newCondition();
    private int permits;

    SemaphoreOnLock(int initialPermits) {
        lock.lock();
        try {
            permits = initialPermits;
        } finally {
            lock.unlock();
        }
    }

    public void acquire() throws InterruptedException {
        lock.lock();
        try {
            while (permits == 0) {
                permitsAvailable.await();
            }
            -- permits;
        } finally {
            lock.unlock();
        }
    }

    public void release() {
        lock.lock();
        try {
            ++ permits;
            permitsAvailable.signal();
        } finally {
            lock.unlock();
        }
    }

}
</pre>

ReentrantLock和Semaphore的实现都使用了一个共同的基类，即AbstractQueuedSynchronizer,这个类也是其他许多同步类的基类。
AQS是一个用于构建锁和同步器的框架，许多同步器都可以通过AQS很容易并且高效地构造处理。

AQS解决了实现同步器时涉及的大量细节问题，例如等待线程采用FIFO队列操作顺序。在不同的同步器中还可以定义一些灵活的标准来判断某个线程是应该通过还是等待。

在基于AQS构建的同步器中，只可能在一个时刻发生阻塞，从而降低上下文切换的开销，并提高吞吐量。在设计AQS时充分考虑了可伸缩性，因此java.util.concurrent中所有基于AQS构建的同步器都能获得这个优势。

### AbstractQueuedSynchronizer

在基于AQS构建的同步器类中，最基本的操作包括各种形式的获取操作和释放操作。
获取操作是一种依赖状态的操作，并且通常会阻塞。
释放并不是一个可阻塞的操作，当执行“释放”操作时，所有在请求时被阻塞的线程都会开始执行。

AQS负责管理同步器类中的状态，它管理了一个整数状态信息，可以通过getState，setState以及compareAndSetState等proteced类型方法来进行操作。这个整数可以用于表示任意状态。
ReentrantLock用它来表示所有者线程以及重复获取该锁的次数，Semaphore用它来表示剩余的许可数量，FutureTask用它来表示任务的状态。

*简单的闭锁*

<pre>
public class OneShotLatch {
    private final Sync sync = new Sync();

    public void singal() {
        sync.releaseShared(0);
    }

    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(0);
    }

    private class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected int tryAcquireShared(int ignored) {
            return (getState() == 1) ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            setState(1);
            return true;
        }
    }
}
</pre>

