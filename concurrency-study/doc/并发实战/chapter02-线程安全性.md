要编写线程安全的代码，其核心在于要对状态访问操作进行管理，特别是对共享的和可变的状态的访问。

对象的状态是指存储在状态变量（例如实例或静态域）中的数据。对象的状态可能包括其他依赖对象的域

“共享”意味着变量可以由多个线程同时访问，“可变”意味着变量的值在其生命周期内可以发生变化。

当多个线程访问某个状态变量并且其中有一个线程执行写入操作时，必须采用同步机制来协同这些线程对变量的访问。

在编写并发应用程序时，一种正确的编程方法就是:首先使代码正确运行，如何再提高代码的速度。

线程安全类中也可以包含非线程安全的类。在任何情况下，只有当类中仅包含自己的状态时，线程安全类才是有意义的。

当多个线程访问某个类时，不管运行时环境采用何种调度方式或者这些线程将如何交替执行，并且在主调代码中不需要任何额外的同步或协同，这个类都能表现出正确的行为，那么就称这个类是线程安全的。

**无状态的对象一定是线程安全的**

<pre>
@ThreadSafe
public class StatelessFactorizer extends HttpServlet {
	@Override
    public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		BigInteger i = extractFromRequest(request);
		BigInteger[] factors = factor(i);
		encodeIntoResponse(response, factors);
	}
}
</pre>

StatelessFactorizer是无状态的：它不包含域也没有引用其他类的域。
**一次特定计算的瞬时状态，会唯一地存在本地变量中，这些本地变量存储在线程的栈中，只有执行的线程才能访问。**
一个访问StatelessFactorizer的线程，不会影响访问同一个Servlet的其他线程的计算结果，因为两个线程不共享状态，它们如同在访问不同的实例

<pre>
@NotThreadSafe
public class UnsafeCountingFactorizer extends HttpServlet {

	private long count = 0;

	public long getCount() {
        return count;
	}

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		BigInteger i = extractFromRequest(request);
		BigInteger[] factors = factor(i);
		++count;
		encodeIntoResponse(response, factors);
	}
}
</pre>
++count不是原子操作:获得当前值，加1，写回新值，这样会导致遗失更新。

### 竞态条件
当某个计算的正确性取决于多个线程的交替执行时序时，那么就会发生竞态条件。
多个线程同时更新共享资源会引发竞态条件。
最常见的竞态条件就是“先检查后执行(Check-Then-Act)”操作，即通过一个可能失效的观测结果来决定下一步的动作。

导致竞态条件发生的代码区称作**临界区**。

**延迟初始化中的竞态条件**

<pre>
@NotThreadSafe
public class LazyInitRace {

	private ExpensiveObject instance = null;

	public ExpensiveObject getInstance() {
        if (instance == null) {
			instance = new ExpensiveObject();
		}
		return instance;
	}
}
</pre>
线程A和B同时执行getInstance，A看的instance是null，并创建一个新的ExpensiveObject。同时B也在检查instance是否为null。此时此刻的instance是否为null，这依赖于不可预测的时序，
包括线程的调度方式，以及A需要花多长时间来初始化ExpensiveObject并设置instance。如果当B检查时，instance为空，那么在两次调用getInstance时可能会得到不同的结果，即使getInstance通常被认为是返回相同的实例。

和大多数并发错误一样，竞态条件并不总是会产生错误，还需要某种不恰当的执行时序。

### 复合操作

要避免竞态条件问题，就必须在某个线程修改该变量时，通过某种方式防止其他线程使用这个变量，从而确保其他线程只能在修改操作完成之前或之后读取和修改状态，而不是在修改状态的过程中。

假定有两个操作A和B，如果从执行A的线程来看，当另一个线程执行B时，要么将B全部执行完，要么完全不执行B，那么A和B对彼此来说是原子的。
**原子操作是指对于访问同一个状态的所有操作（包括该操作本身）来说，这个操作是一个以原子方式执行的操作**

为了确保线程安全性，”先检查后执行“和”读取-修改-写入“等操作必须是原子的。
我们将”先检查后执行“以及”读取-修改-写入“等操作统称为复合操作；包含了一组必须以原子方式执行的操作以确保线程安全性。

<pre>
@ThreadSafe
public class CountingFactorizer extends HttpServlet {

	private final AtomicLong count = new AtomicLong(0);

	public long getCount() {
        return count.get();
	}

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		BigInteger i = extractFromRequest(request);
		BigInteger[] factors = factor(i);
		count.incrementAndGet();
		encodeIntoResponse(response, factors);
	}
}
</pre>

通过用AtomicLong来代替long类型的计数器，能够确保所有对计数器状态的访问操作都是原子的。由于Servlet的状态就是计数器的状态，并且计数器是线程安全的，因此Servlet也是安全的。

当在无状态的类中添加一个状态时，如果该状态完全由线程安全的对象来管理，那么这个类仍然是线程安全的。

相比于非线程安全对象，判断一个线程安全对象的可能状态和状态的转换要容易的多，从而也简化了维护和验证线程安全性的工作。

### 加锁机制

当状态变量的数量由一个变为多个时，并不会像状态变量由零个变为一个那样简单。

<pre>
@NotThreadSafe
public class UnsafeCachingFactorizer extends HttpServlet {

	private final AtomicReference<BigInteger> lastNumber = new AtomicReference<BigInteger>();
	private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<BigInteger[]>();

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		BigInteger i = extractFromRequest(request);
		if (i.equals(lastNumber.get())) {
			encodeIntoResponse(response, lastFactors.get());
		} else {
			BigInteger[] factors = factor(i);
			lastNumber.set(i);
			lastFactors.set(factors);
			encodeIntoResponse(response, factors);
		}
	}

}
</pre>

在线程的安全性的定义中要求，多个线程之间的操作无论采用何种执行时序或交替方式，都要保证不变条件不被破坏。
UnsafeCatchFactorizer的不变条件之一是：在lastFactors中缓存的各个因子的乘积应该等于缓存在lastNumber中的数值。
只有确保了这个不变性不被破坏，上面的Servlet才是正确的。

当在不变性条件中涉及多个变量时，各个变量之间并不是彼此独立的，而是某个变量的值会对其他变量产生约束。因此，当更新某个变量时，需要在同一个原子操作中对其他变量同时进行更新。

在使用原子引用的条件下，尽管对set方法的每次调用都是原子的，但仍然无法同时更新lastNumber和lastFactors。如果只改变了其中一个变量，那么在这两次修改操作之间，其他线程将发现不变性条件被破坏了。
同样，我们也不能保证会同时获取两个值：在线程A获取这两个值的过程中，线程B可能修改了它们，这样线程A也会发现不变性条件被破坏了。

要保持状态的一致性，就需要在单个原子操作中更新所有相关的状态变量。

#### 内置锁

同步代码块包括两个部分：一个作为锁的对象引用，一个作为由整个锁保护的代码块。

每个Java对象都可以用做一个实现同步的锁，这些锁被称为内置锁或者监视器锁。线程在进入同步代码块之前会自动获得锁，并且在退出同步代码块时自动释放锁。

<pre>
@ThreadSafe
public class SynchronizedFactorizer extends HttpServlet {

	@GuardedBy("this")
    private BigInteger lastNumber;

	@GuardedBy("this")
	private BigInteger[] lastFactors;

	@Override
	public synchronized void service(ServletRequest request,
			ServletResponse response) throws ServletException, IOException {
		BigInteger i = extractFromRequest(request);
		if (i.equals(lastNumber)) {
			encodeIntoResponse(response, lastFactors);
		} else {
			BigInteger[] factors = factor(i);
			lastNumber = i;
			lastFactors = factors;
			encodeIntoResponse(response, factors);
		}
	}

}
</pre>

#### 重入

当某个线程请求一个由其他线程持有的锁时，发出请求的线程就会阻塞。然而由于内置锁是可以重入的，因此如果某个线程试图获得一个已经由它自己持有的锁，那么这个请求就会成功。
重入意味着获取锁的操作的粒度是“线程”，而不是“调用”。

**重入的简单实现**
重入的一种实现方式是，为每个锁管理一个获取计数器和一个所有者线程。当这个计数器的值为0时，这个所就被认为是没有被任何线程持有。当线程请求一个未被持有的锁时，JVM将记下
锁的持有者，并且将获取计数值置为1.如果同一个线程再次获取这个锁，计数值将递增，而当线程退出同步代码块时，计数器会相应递减。当计数器的值为0时，这个锁将被释放。

**synchronized是可重入的**

*使用synchronized实现一个不可重入的Lock*

<pre>
public class Lock {

    private boolean isLocked = false;

    public synchronized  void lock() throws InterruptedException {
        while (isLocked) {//自旋锁
            wait();
        }
        isLocked = true;
    }

    public synchronized void unLock() {
        isLocked = false;
        notifyAll();
    }
}
</pre>

<pre>
public class Reentrant {
    private Lock lock = new Lock();
    public void outer() throws InterruptedException {
        lock.lock();
        inner();
        lock.unLock();
    }

    public void inner() throws InterruptedException {
        lock.lock();
        //do something
        lock.unLock();
    }
}
</pre>
因为Lock是不可重入的,outer()获得锁后调用inner()会阻塞

*重构实现可重入的Lock*

<pre>
public class ReentrantLock {
    private boolean isLocked = false;
    private Thread lockedBy = null;
    private int lockedCount = 0;

    public synchronized void lock() throws InterruptedException {
        Thread callingThread = Thread.currentThread();
        while (isLocked && lockedBy != callingThread) {
            wait();
        }
        isLocked = true;
        lockedCount ++;
        lockedBy = callingThread;
    }

    public synchronized void unLock() {
        Thread callingThread = Thread.currentThread();
        if (callingThread == Thread.currentThread()) {
            lockedCount --;
            if (lockedCount == 0) {
                lockedBy = null;
                isLocked = false;
                notifyAll();
            }
        }
    }
}
</pre>

### 用锁来保护对象

对于可能被多个线程同时访问的可变状态变量，在访问它时都需要持有同一个锁，在这个情况下，我们称状态变量是由这个锁保护的。

**每个共享和可变的变量都应该只由一个锁来保护，从而使维护人员知道是哪一个锁**

**并非所有的数据都需要锁的保护，只有被多个线程同时访问的可变数据才需要通过锁来保护**

**对于每个包含多个变量的不变性条件，其中涉及的所有变量都需要由同一个锁来保护。**

Vector的每个方法都是同步方法，但是并不足以确保Vector上复合操作都是原子的

<pre>
if (!vector.contains(element)) {
    vector.add(element);
}
</pre>
虽然synchronized方法可以确保单个操作的原子性，但是如果要把多个操作合并为一个复合操作，还是需要额外的加锁机制。

将每个方法都作为同步方法还可能导致活跃性问题和性能问题。

### 活跃性和性能

<pre>
@ThreadSafe
public class CachedFactorizer extends HttpServlet {

	@GuardedBy("this")
	private BigInteger lastNumber;

	@GuardedBy("this")
	private BigInteger[] lastFactors;

	@GuardedBy("this")
	private long hits;

	@GuardedBy("this")
	private long cacheHits;

	public synchronized long getHits() {
		return hits;
	}

	public synchronized double getCacheHitRatio() {
		return (double) cacheHits / (double) hits;
	}

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		BigInteger i = extractFromRequest(request);
		BigInteger[] factors = null;
		synchronized (this) {
			++hits;
			if (i.equals(lastNumber)) {
				++cacheHits;
				factors = lastFactors.clone();
				// encodeIntoResponse(response, lastFactors);
			}
		}
		if (factors == null) {
			factors = factor(i);
			synchronized (this) {
				lastNumber = i;
				lastFactors = factors.clone();
			}
		}
		encodeIntoResponse(response, factors);
	}

}
</pre>

通过缩小同步代码块的作用范围，我们很容易做到既确保Servlet的并发性，同时又维护线程安全性。

要确保同步代码块不要过小，并且不要将本应是原子操作拆分到多个同步代码块中。应该尽量将不影响共享状态且执行时间较长的操作从同步代码块中分离出去，从而在这些操作的执行过程中，其他线程可以访问共享状态。