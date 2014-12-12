设计线程安全的类

**使用Java监视器模式的线程安全计数器**

<pre>
@ThreadSafe
public final class Counter {
    @GuardedBy("this") private long value = 0;

    public synchronized long getValue() {
        return value;
    }

    public synchronized long increment() {
        if (value == Long.MAX_VALUE)
            throw new IllegalStateException("counter overflow");
        return ++value;
    }
}
</pre>

### 实例封闭

**通过封闭机制来确保线程安全**

<pre>
@ThreadSafe
public class PersonSet {
    @GuardedBy("this") private final Set<Person> mySet = new HashSet<Person>();

    public synchronized void addPerson(Person p) {
        mySet.add(p);
    }

    public synchronized boolean containsPerson(Person p) {
        return mySet.contains(p);
    }

    interface Person {
    }
}
</pre>

#### Java监视器模式

遵循Java监视器模式的对象会把对象的所有可变状态都封装起来，并由对象自己的内置锁来保护

**通过一个私有锁来保护对象**

<pre>
public class PrivateLock {
    private final Object myLock = new Object();
    @GuardedBy("myLock") Widget widget;

    void someMethod() {
        synchronized (myLock) {
            // Access or modify the state of widget
        }
    }
}
</pre>

<pre>
@NotThreadSafe
public class MutablePoint {
    public int x, y;

    public MutablePoint() {
        x = 0;
        y = 0;
    }

    public MutablePoint(MutablePoint p) {
        this.x = p.x;
        this.y = p.y;
    }
}
</pre>

<pre>
@ThreadSafe
 public class MonitorVehicleTracker {
    @GuardedBy("this") private final Map<String, MutablePoint> locations;

    public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
        this.locations = deepCopy(locations);
    }

    public synchronized Map<String, MutablePoint> getLocations() {
        return deepCopy(locations);
    }

    public synchronized MutablePoint getLocation(String id) {
        MutablePoint loc = locations.get(id);
        return loc == null ? null : new MutablePoint(loc);
    }

    public synchronized void setLocation(String id, int x, int y) {
        MutablePoint loc = locations.get(id);
        if (loc == null)
            throw new IllegalArgumentException("No such ID: " + id);
        loc.x = x;
        loc.y = y;
    }

    //deepCopy并不只是用unmodifiableMap来包装Map,因为这只能防止容器对象被修改，而不能
    //防止调用者修改保存在容器中的可变对象。基于同样的原因，如果只是通过拷贝构造函数
    //来填充deepCopy中的HashMap，那么同样是不正确的，因为这样做只复制了指向Point对象的引用，
    //而不是Point本身
    private static Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> m) {
        Map<String, MutablePoint> result = new HashMap<String, MutablePoint>();

        for (String id : m.keySet())
            result.put(id, new MutablePoint(m.get(id)));

        return Collections.unmodifiableMap(result);
    }
}
</pre>

### 线程安全性的委托

**将线程安全委托给ConcurrentHashMap**

<pre>
@Immutable
public class Point {
    public final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
</pre>

<pre>
@ThreadSafe
public class DelegatingVehicleTracker {
    private final ConcurrentMap<String, Point> locations;
    private final Map<String, Point> unmodifiableMap;

    public DelegatingVehicleTracker(Map<String, Point> points) {
        locations = new ConcurrentHashMap<String, Point>(points);
        unmodifiableMap = Collections.unmodifiableMap(locations);
    }

    public Map<String, Point> getLocations() {
        return unmodifiableMap;
    }

    public Point getLocation(String id) {
        return locations.get(id);
    }

    public void setLocation(String id, int x, int y) {
        if (locations.replace(id, new Point(x, y)) == null)
            throw new IllegalArgumentException("invalid vehicle name: " + id);
    }

    // Alternate version of getLocations (Listing 4.8)
    public Map<String, Point> getLocationsAsStatic() {
        return Collections.unmodifiableMap(
                new HashMap<String, Point>(locations));
    }
}
</pre>

由于Point类是不可变的，因而它是线程安全的。

#### 独立的状态变量

可以讲线程安全性委托给多个状态变量，只要这些变量是彼此独立的，即组合成的类并不会再其包含的多个状态变量上增加任何不变性条件。

<pre>
public class VisualComponent {
    private final List<KeyListener> keyListeners
            = new CopyOnWriteArrayList<KeyListener>();
    private final List<MouseListener> mouseListeners
            = new CopyOnWriteArrayList<MouseListener>();

    public void addKeyListener(KeyListener listener) {
        keyListeners.add(listener);
    }

    public void addMouseListener(MouseListener listener) {
        mouseListeners.add(listener);
    }

    public void removeKeyListener(KeyListener listener) {
        keyListeners.remove(listener);
    }

    public void removeMouseListener(MouseListener listener) {
        mouseListeners.remove(listener);
    }
}
</pre>

#### 失效的委托

<pre>
	@NotThreadSafe
	public class NumberRange {
	    // INVARIANT: lower &lt;= upper
	    private final AtomicInteger lower = new AtomicInteger(0);
	    private final AtomicInteger upper = new AtomicInteger(0);
	
	    public void setLower(int i) {
	        // Warning -- unsafe check-then-act
	        if (i &gt; upper.get())
	            throw new IllegalArgumentException("can't set lower to " + i + " &gt; upper");
	        lower.set(i);
	    }
	
	    public void setUpper(int i) {
	        // Warning -- unsafe check-then-act
	        if (i &lt; lower.get())
	            throw new IllegalArgumentException("can't set upper to " + i + " &lt; lower");
	        upper.set(i);
	    }
	
	    public boolean isInRange(int i) {
	        return (i &gt;= lower.get() && i &lt;= upper.get());
	    }
	}
</pre>

NumberRange不是线程安全的，没有维持对下界和上界进行约束的不变性条件。
setLower和setUpper都是“先检查后执行”的操作，但是它们没有使用足够的加锁机制来保证这些操作的原子性。

虽然AtomicInteger是线程安全的，但是经过组合得到的类却不是.由于状态变量lower和upper不是彼此独立的，因此NumberRange不能将线程安全性委托给它的线程安全状态变量。 

NumberRange可以通过加锁机制来维护不变性条件以确保其线程安全性。

如果某个类含有复合操作，那么仅靠委托并不足以实现线程安全性。在这种情况下，这个类必须提供给自己的加锁机制以保证这些复合操作是原子操作，除非整个复合操作都可以委托给状态变量。

#### 发布底层的状态变量

**如果一个状态变量是线程安全的，并且没有任何不变性条件来约束它的值，在变量的操作上也不存在任何不允许的状态转换，那么就可以安全地发布这个对象**

*线程安全且可变的Point*

<pre>
@ThreadSafe
public class SafePoint {

	@GuardedBy("this")
    private int x;
	private int y;

	private SafePoint(int[] a) {
		 this(a[0], a[1]);
	}

	public SafePoint(SafePoint p) {
		this(p.get());
	}

	public SafePoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public synchronized int[] get() {
		return new int[] { x, y };
	}

	public synchronized void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
</pre>

*Point是可变的*

<pre>
@ThreadSafe
public class PublishingVehicleTracker {

	private final Map<String, SafePoint> locations;
    private final Map<String, SafePoint> unmodifiableMap;

	public PublishingVehicleTracker(Map<String, SafePoint> locations,
			Map<String, SafePoint> unmodifiableMap) {
		this.locations = new ConcurrentHashMap<String, SafePoint>(locations);
		this.unmodifiableMap = Collections.unmodifiableMap(this.locations);
	}

	public synchronized Map<String, SafePoint> getLocations() {
		return locations;
	}

	public synchronized Map<String, SafePoint> getUnmodifiableMap() {
		return unmodifiableMap;
	}

	public void setLocation(String id, int x, int y) {
		if (!locations.containsKey(id)) {
			throw new IllegalArgumentException("invalid vehicle name: " + id);
		}
		locations.get(id).set(x, y);
	}
}
</pre>

getLocations返回底层的map对象的一个不可变副本。调用者不能增加或删除车辆，但却可以通过修改返回Map的SafePoint值来改变车辆的位置。
PublishingVehicleTracker是线程安全的，但如果它在车辆位置的有效值上施加任何约束，那么就不再是线程安全的。

### 在现有的线程安全类中添加功能

#### 修改原始的类

#### 扩展原始的类
**扩展Vector并增加一个“若没有则添加”方法**

<pre>
@ThreadSafe
public class BetterVector<E> extends Vector<E> {

	public synchronized boolean putIfAbsent(E x) {
        boolean absent = !contains(x);
		if (absent) {
			add(x);
		}
		return absent;
	}
}
</pre>

扩展方法比直接将代码添加到类中更加脆弱，因为现有的同步策略实现被分布到多个单独维护的源代码文件中。如果底层的类修改了同步策略并选择不同的锁来保护它的状态变量，那么子类会被破坏

#### 客户端加锁机制
通过扩展类的功能，但并不是扩展类本身，而是将扩展代码放入一个辅助类中。

*非线程安全*

<pre>
@NotThreadSafe
public class ListHelper<E> {

	public List<E> list = Collections.synchronizedList(new ArrayList<E>());

	public synchronized boolean putIfAbsent(E x) {
        boolean absent = !list.contains(x);
		if (absent) {
			list.add(x);
		}
		return absent;
	}

}
</pre>

上述代码在错误的锁上进行了同步。ListHelper只是带来了同步的假象；
即使list的所有操作都被声明为synchronized，但使用了不同锁。putIfAbsent操作对于List的其他操作来说并不是原子的。

为了让这个方法正确工作，必须使List在实现客户端加锁或外部加锁时所用的锁是同一个锁。

客户端加锁是指，对于使用某个对象X的客户端代码，使用X本身用于保护其状态的锁来保护这段客户代码。为了使用客户端加锁，你必须知道对象X使用了哪个锁。

*通过客户端加锁来实现“若没有则添加”*

<pre>
@ThreadSafe
class GoodListHelper <E> {
    public List<E> list = Collections.synchronizedList(new ArrayList<E>());

    public boolean putIfAbsent(E x) {
        synchronized (list) {
            boolean absent = !list.contains(x);
            if (absent)
                list.add(x);
            return absent;
        }
    }
}
</pre>

#### 组合

*通过组合实现“若没有则添加”*

<pre>
@ThreadSafe
public class ImprovedList<T> implements List<T> {
    private final List<T> list;

    /**
     * PRE: list argument is thread-safe.
     */
    public ImprovedList(List<T> list) { this.list = list; }

    public synchronized boolean putIfAbsent(T x) {
        boolean contains = list.contains(x);
        if (contains)
            list.add(x);
        return !contains;
    }

    // Plain vanilla delegation for List methods.
    // Mutative methods must be synchronized to ensure atomicity of putIfAbsent.
    
}
</pre>

 ImprovedList 通过自身的内置锁增加了一层额外的加锁。它并不关心底层的 List 是否是线程安全的，即使 List 不是线程安全的或者修改了它的枷锁方式，Improved 也会提供一致的加锁机制来实现线程安全性。虽然额外的同步层可能导致轻微的性能损失，但与模拟另一个对象的加锁策略相比，ImprovedList 更为健壮。事实上，我们使用了 Java 监视器模式来封装现有 List ，并且只要在类中拥有指向底层 List 的为意外不引用，就能确保线程安全性。 