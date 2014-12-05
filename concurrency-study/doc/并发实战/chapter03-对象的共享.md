### 可见性

通常，我们无法确保执行读操作的线程能适时地看到其他线程写入的值，有时甚至是根本不可能的事情。为了确保多个线程之间对内存写入操作的可见性，必须使用同步机制。

<pre>
public class NoVisibility {

	private static boolean ready;
    private static int number;

	private static class ReadThread extends Thread {
		@Override
		public void run() {
			while (!ready) {
				Thread.yield();
			}
			System.out.println(number);
		}
	}

	public static void main(String[] args) {
		new ReadThread().start();
		number = 42;
		ready = true;
	}
}
</pre>

NoVisibility可能会持续循环下去，因为读线程可能永远看不到ready的值。
一种更奇怪的现象是：NoVisibility可能会输出0，因为读线程可能看到了写入ready的值，但却没有看到之后写入number的值，这种现象被称为**重排序**

在没有同步的情况下，编译器、处理器以及运行时等都可能对操作的执行顺序进行一些意向不到的调整。在缺乏足够同步的多线程程序中，要想对内存操作的执行顺序进行判断，几乎无法得出正确的结论。

*重排序*的讲解
http://tech.meituan.com/java-memory-reordering.html

**只要有数据在多个线程之间共享，就使用正确的同步**

#### 失效数据

当读线程查看ready变量时，可能会得到一个已经失效的值。除非在每次访问变量都是同步的，否则很可能得到该变量的一个失效值。
更糟糕的是，失效值可能不会同时出现：一个线程可能获得某个变量的最新值，而获得另一个变量的失效值。

*非线程安全的可变整数类*

<pre>
@NotThreadSafe
public class MutableInteger {

	private int value;

	public int getValue() {
        return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
</pre>
如果某个线程调用了set，那么另一个正在调用get的线程可能会看到更新后的value值，也可能看不到。

*线程安全的可变整数类*

<pre>
@ThreadSafe
public class SynchronizedInteger {

	@GuardedBy("this")
    private int value;

	public synchronized int getValue() {
		return value;
	}

	public synchronized void setValue(int value) {
		this.value = value;
	}

}
</pre>

#### 非原子的64位操作

java内存模型要求，变量的读取操作和写入操作都必须是原子操作，但对于非volatile类型的long和double变量，JVM允许将64位的读操作或写操作分解为两个32位的操作。
当读取一个非volatile类型的long变量时，如果对该变量的读操作和写操作在不同的线程中执行，那么很可能会读取到某个值的高32位和另一个值的低32位。
因此，即使不考虑失效数据的问题，在多线程程序中使用共享且可变的long和double等类型的变量是不安全的，除非用volatile来声明它们，或者用锁保护起来。

#### 加锁与可见性

#### volatile变量

volatile变量用来确保将变量的更新操作通知到其他线程。当把变量声明为volatile类型后，编译器与运行时都会注意到这个变量是共享的，因此不会将该变量上的操作与其他内存操作一起重排序。
volatile变量不会被缓存在寄存器或者对其他处理器不可见的地方，因此在读取volatile类型的变量时总会返回最新写入的值。

仅当volatile变量能简化代码的实现以及对同步策略的验证时，才应该使用它们。

加锁机制既可以确保可见性又可以确保原子性，而volatile变量只能保证可见性

当且仅当满足以下条件时，才应该使用volatile变量：
- 对于变量的写入操作部依赖于变量的当前值，或者你能够确保只有单个线程更新变量的值。
- 该变量不会与其他状态变量一起纳入不变性条件中
- 在访问变量时不需要加锁

http://www.ibm.com/developerworks/cn/java/j-jtp06197.html

### 发布和逸出

发布一个对象的意思是指：使对象能够在当前作用域之外的代码中使用。
例如，将一个指向该对象的引用保存到其他代码可以访问的地方，或者在某个非私有的方法中返回该引用，或者将引用传递到其他类的方法中。

当某个不应该发布的对象被发布时，这种情况就被称为逸出。

*发布一个对象*

<pre>
public static Set<Secret> knownSecrets;

public void initialize() {
    knownSecrets = new HashSet<Secret>();
}
</pre>

发布某个对象时，可能会间接地发布其他对象。上述代码间接的发布了Secret对象，因为任何代码都可以遍历这个集合，并获得对这个新Secret对象的引用。

如果从非私有方法中返回一个引用，那么同样会发布返回的对象。

*使内部的可变状态逸出*

<pre>
public class UnsafeStates {

    private String[] states = new String[] {
            "AK", "AL"
    };

    public String[] getStates() {
        return states;
    }
}
</pre>

上述代码的任何调用者都能修改这个数组的内容，数组states已经逸出了它所在的作用域，因为这个本该是私有的变量已经被发布了。

当发布一个对象时，在该对象的非私有域中引用的所有对象同样会发布。
如果一个已经发布的对象能够通过非私有的变量引用和方法调用到达其他的对象，那么这些对象也都已经被发布。

隐式地使用this引用逸出：发布一个内部的类实例

当从对象的构造函数发布对象时，只是发布了一个尚未构造完成的对象。即使发布对象的语句位于构造函数的最后一行也是如此。
**不要在构造过程中使用this引用逸出**

*隐式地使用this引用逸出*

<pre>
public class ThisEscape {
    public ThisEscape(EventSource source) {
        source.registerListener(new EventListener() {
            public void onEvent(Event e) {
                doSomething(e);
            }
        });
    }

    void doSomething(Event e) {
    }


    interface EventSource {
        void registerListener(EventListener e);
    }

    interface EventListener {
        void onEvent(Event e);
    }

    interface Event {
    }
}
</pre>

在构造过程中使用this引用逸出的一个常见错误是：在构造函数中启动一个线程。
当对象在其构造函数中创建一个线程时，无论是显示创建（通过将它传递给构造函数）还是隐式创建（由于Thread或Runnable是该对象的一个内部类），this引用都会被新创建的线程共享。
在对象尚未完全构造之前，新的线程就可以看见它。在构造函数中创建线程并没有错误，但最好不要立即启动它，而是通过一个start或initialize方法来启动。
在构造函数中调用一个可改写的实例方法，同样会导致this引用在构造过程中逸出。

*使用工厂方法来防止this引用在构造过程中逸出*

<pre>
public class SafeListener {
    private final EventListener listener;

    private SafeListener() {
        listener = new EventListener() {
            public void onEvent(Event e) {
                doSomething(e);
            }
        };
    }

    public static SafeListener newInstance(EventSource source) {
        SafeListener safe = new SafeListener();
        source.registerListener(safe.listener);
        return safe;
    }

    void doSomething(Event e) {
    }


    interface EventSource {
        void registerListener(EventListener e);
    }

    interface EventListener {
        void onEvent(Event e);
    }

    interface Event {
    }
}
</pre>

### 线程封闭

当访问共享的可变数据时，通常需要使用同步。一种避免使用同步的方式就是不共享数据。如果仅在单线程内访问数据，就不需要同步。这种技术被称为**线程封闭**

当某个对象封闭在一个线程中时，这种用法将自动实现线程安全性，即使被封闭的对象本身不是线程安全的。

#### Ad-hoc线程封闭

#### 栈封闭

在栈封闭中，只能通过局部变量才能访问对象。

*基本类型的局部变量与引用变量的线程封闭性*

<pre>
public int loadTheArk(Collection<Animal> candidates) {
        SortedSet<Animal> animals;
        int numPairs = 0;
        Animal candidate = null;

        // animals confined to method, don't let them escape!
        animals = new TreeSet<Animal>(new SpeciesGenderComparator());
        animals.addAll(candidates);
        for (Animal a : animals) {
            if (candidate == null || !candidate.isPotentialMate(a))
                candidate = a;
            else {
                ark.load(new AnimalPair(candidate, a));
                ++numPairs;
                candidate = null;
            }
        }
        return numPairs;
    }
</pre>

只有一个引用指向animals,这个引用被封闭在局部变量中，因此也被封闭在执行线程中。然而如果发布了对集合animals（或者该对象中的任何内部数据）的引用，那么封闭性将被破坏，并导致对象animals的逸出

#### ThreadLocal

ThreadLocal对象通常用于防止对可变的单实例变量或全局变量进行共享。

*使用ThreadLocal来维持线程封闭性*

<pre>
public class ConnectionDispenser {
    static String DB_URL = "jdbc:mysql://localhost/mydatabase";

    private ThreadLocal<Connection> connectionHolder
            = new ThreadLocal<Connection>() {
                public Connection initialValue() {
                    try {
                        return DriverManager.getConnection(DB_URL);
                    } catch (SQLException e) {
                        throw new RuntimeException("Unable to acquire Connection, e");
                    }
                };
            };

    public Connection getConnection() {
        return connectionHolder.get();
    }
}
</pre>


### 不变性

如果某个对象在创建后其状态就不能被修改，那么这个对象就称为不可变对象。

**不可变对象一定是线程安全的**

当满足以下条件时，对象才是不可变的
- 对象创建以后其状态就不能修改
- 对象的所有域都是final类型
- 对象是正确创建的（在对象创建期间，this引用没有逸出）

即使对象中所有的域都是final类型的，这个对象也仍然是可变的，因为在final类型的域中可以保存对可变对象的引用。

在不可变对象的内部仍然可以使用可变对象来管理它们的状态

*在可变对象基础上构建的不可变类*

<pre>
@Immutable
public final class ThreeStooges {

	private final Set<String> stooges = new HashSet<String>();

	public ThreeStooges() {
        stooges.add("Moe");
		stooges.add("Larry");
		stooges.add("Curly");
	}

	public boolean isStooge(String name) {
		return stooges.contains(name);
	}

}
</pre>

stooges构造完成之后是无法对其进行修改。

#### final域

**除非需要更高的可见性，否则应该将所有的域都声明为私有域。**

**除非需要某个域是可变的，否则应将其声明为final域**

*使用volatile类型来发布不可变对象*

<pre>
@Immutable
public class OneValueCache {
    private final BigInteger lastNumber;
    private final BigInteger[] lastFactors;

    public OneValueCache(BigInteger i,
                         BigInteger[] factors) {
        lastNumber = i;
        lastFactors = Arrays.copyOf(factors, factors.length);
    }

    public BigInteger[] getFactors(BigInteger i) {
        if (lastNumber == null || !lastNumber.equals(i))
            return null;
        else
            return Arrays.copyOf(lastFactors, lastFactors.length);
    }
}
</pre>

<pre>
@ThreadSafe
public class VolatileCachedFactorizer extends HttpServlet {

	private volatile OneValueCache cache = new OneValueCache(null, null);

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		BigInteger i = extractFromRequest(request);
		BigInteger[] factors = cache.getFactors(i);
		if (factors == null) {
			factors = factor(i);
			cache = new OneValueCache(i, factors);
		}
		encodeIntoResponse(response, factors);
	}

}
</pre>

### 安全发布

*在没有足够同步的情况下发布对象*

<pre>
public class StuffIntoPublic {
    public Holder holder;

    public void initialize() {
        holder = new Holder(42);
    }
}
</pre>

上述代码会导致其他线程看到尚未创建完成的对象

<pre>
public class Holder {
    private int n;

    public Holder(int n) {
        this.n = n;
    }

    public void assertSanity() {
        if (n != n)
            throw new AssertionError("This statement is false.");
    }
}
</pre>

由于没有使用同步来确保Holder对象对其他线程可见，因此将Holder称为“未被正确发布”。在未被正确发布的对象中存在两个问题。
首先，除了发布对象的线程外，其他线程可以看到的Holder域是一个失效值，因此将看到一个空引用或者之前的旧值。
然而，更糟糕的情况是，线程看到Holder引用的值是最新的，但Holder状态的值却是失效的。情况变得更加不可预测的是，某个线程在第一次读取域时得到失效值，而再次读取这个域时会得到一个更新值，这也是assertSainty抛出AssertionError的原因。

 不可变对象与初始化安全性：

**任何线程都可以在不需要额外同步的情况下安全地访问不可变对象，即使在发布这些对象的时候没有使用同步**

**安全发布地常用模式**
要安全地发布一个对象，对象的引用以及对象的状态必须同时对其他线程可见。一个正确构造的对象可以通过以下方式来安全地发布：
- 在静态初始化函数中初始化一个对象引用。
- 将对象的引用保存在volatile类型的域或者AtomicReferance对象中
- 将对象的引用保存到某个正确构造对象的final类型域中
- 将对象的引用保存到一个由锁保护的域中


但线程安全库中的容器类提供了以下的安全发布保证：

- 通过将一个键或者值放入Hashtable、synchronizedMap或者ConcurrentMap中，可以安全地将它发布给任何从这些容器中访问它的线程（无论是直接访问还是通过迭代器访问）
- 通过将某个元素放入Vector、CopyOnWriteArrayList、CopyOnWriteArraySet、synchronizedList或synchronizedSet中，可以将该元素安全地发布到任何从这些容器中访问该元素的线程
- 通过将某个元素放入BlockingQueue或者ConcurrentLinkedQueue中，可以将该元素安全地发布到任何从这些队列中访问该元素的线程。

最简单的线程安全的对象发布，是通过静态的初始化器：
   public static Holder hold=new Holder(42);
   
**事实不可变对象**
   
如果对象从技术上来看是可变的，但其状态在发布后不会再改变，称这种对象为"事实不可变对象"。

在没有额外的同步的情况下，任何线程都可以安全地使用被安全发布的事实不可变对象。

例如，Date本身是可变的，但如果将它作为不可变对象来使用，那么在多个线程之间共享Date对象时，就可以省去对锁的使用。假设需要维护一个Map对象，其中保存了每位用户的最近登录时间：
                public Map<String, Date> lastLogin =Collections.synchronizedMap(new HashMap<String, Date>());

 如果Date对象的值在被放入Map后就不会改变，那么synchronizedMap中的同步机制就足以使Date值被安全地发布，并且在访问这些Date值时不需要额外的同步。
    
**可变对象**   

如果对象在构造后可以修改，那么安全发布只能确保“发布当时”状态的可见性。对于可变对象，不仅在发布对象时需要使用同步，而且在每次对象访问时同样需要使用同步来确保后续修改操作的可见性。要安全地共享可变对象，这些对象就必须被安全地发布，并且必须是线程安全的或者由某个锁保护起来。
 
对象的发布需求取决于它的可变性：

- 不可变对象可以通过任意机制来发布
- 事实不可改变必须通过安全方式发布
- 可变对象必须通过安全方式发布，并且必须是线程安全的或者由某个锁保护起来