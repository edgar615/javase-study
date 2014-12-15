### 性能与可伸缩性

### 线程引入的开销
#### 上下文切换
即线程的调度。当可运行的线程数量大于CPU的数量的时候发生。将新调度进来的线程执行上下文设置为当前上下文。学过安卓开发的知道安卓有一个非常重要的域就是Context(上下文)。

当线程由于等待某个线程发生竞争的锁而被阻塞时，JVM通过会将这个线程挂起，并允许它交换出去。如果线程频繁地发生阻塞，那么 它们将无法使用完整的调度时间片，那么就发生越多的上下文切换，增加调度开销，并因此降低吞吐量。

大多数通用的处理器中，上下文的切换相当于5k到10k的时钟周期，也就是几微秒。

#### 内存同步
同步操作的性能开销包括多个方面，在synchronized和volatile提供的可见性保证中可能会使用一些特殊命令，即内存栅栏。

在内存栅栏中，大多数操作都是不能被重排序的。

JVM可以通过优化来去掉一些不会发生竞争的锁，如：

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

    public String getStoogeNames() {
        List<String> stooges = new Vector<String>();
        stooges.add("Moe");
        stooges.add("Larry");
        stooges.add("Curly");
        return stooges.toString();
    }
}
</pre>

在getStoogeNames的执行过程中，至少会将vector上的锁获取和释放4次，每次调用add或toString时都会执行1次。
然而，一个智能的运行时编译器通常会分析这些调用，从而使stooges及其内部状态不会逸出，因此可以去掉这4次对锁获取操作。

#### 阻塞

JVM在实现阻塞行为时，可以采用自旋等待，或者通过操作系统挂起被阻塞的线程。
这两种方式的效率高低，要取决于上下文切换的开销以及在成功获取锁之前需要等待的时间。
如果等待的时间较短，则适合采用自旋等待方式，而如果等待时间较长，则适合采用线程挂起方式。

当线程无法获取某个锁或者由于在某个条件等待或在I/O操作上阻塞时，需要被挂起，在这个过程中将包含两次额外的上下文切换，以及所有必要的操作系统操作和缓存操作：被阻塞的线程在其执行时间片还未用完之前就被交换出去，而在随后当要获取的锁或者其他资源可用时，又再次被切换回来。

### 减少锁的竞争
串行操作会降低可伸缩性，并且上下文切换也会降低性能。在锁上发生竞争时同时导致这两种问题，因此减少锁的竞争能够提高性能和可伸缩性。

***在并发程序中，对可伸缩性的最主要威胁就是独占方式的资源锁*

有两个因素将影响在锁上发生竞争的可能性：锁的请求频率，以及每次持有该锁的时间。
如果二者的乘积很小，那么大多数获取锁的操作都不会竞争，因此在该锁上的竞争不会对可伸缩性造成严重影响。
然而，如果在锁上的请求很高，那么需要获取该锁的线程将被阻塞并等待。在极端情况下，即使仍有大量工作等待完成，处理器也会闲置。

有三种方式可以降低锁的竞争程度
    
    - 减少锁的持有时间
    - 降低锁的请求频率
    - 使用带有协调机制的独占锁，这些机制允许更高的并发性
    
#### 缩小锁的范围

减少锁的持有时间

*将一个锁不必要地持有过长时间*

<pre>
@ThreadSafe
public class AttributeStore {
    @GuardedBy("this") private final Map<String, String>
            attributes = new HashMap<String, String>();

    public synchronized boolean userLocationMatches(String name,
                                                    String regexp) {
        String key = "users." + name + ".location";
        String location = attributes.get(key);
        if (location == null)
            return false;
        else
            return Pattern.matches(regexp, location);
    }
}
</pre>

上述代码其实只有map.get这个方法才真正需要锁。

*减少锁的持有时间*

<pre>
@ThreadSafe
public class BetterAttributeStore {
    @GuardedBy("this") private final Map<String, String>
            attributes = new HashMap<String, String>();

    public boolean userLocationMatches(String name, String regexp) {
        String key = "users." + name + ".location";
        String location;
        synchronized (this) {
            location = attributes.get(key);
        }
        if (location == null)
            return false;
        else
            return Pattern.matches(regexp, location);
    }
}
</pre>

#### 缩小锁的粒度
降低锁的请求频率，可以通过锁分解和锁分段等技术来实现，在这些技术中采用多个相互独立的锁来保护独立的状态变量，从而改变这些变量在之前由单个锁来保护的情况。
**然而，使用的锁越多，那么发生死锁的风险也就越高**

*锁分解前*

<pre>
@ThreadSafe
public class ServerStatusBeforeSplit {
    @GuardedBy("this") public final Set<String> users;
    @GuardedBy("this") public final Set<String> queries;

    public ServerStatusBeforeSplit() {
        users = new HashSet<String>();
        queries = new HashSet<String>();
    }

    public synchronized void addUser(String u) {
        users.add(u);
    }

    public synchronized void addQuery(String q) {
        queries.add(q);
    }

    public synchronized void removeUser(String u) {
        users.remove(u);
    }

    public synchronized void removeQuery(String q) {
        queries.remove(q);
    }
}
</pre>

*锁分解后*

<pre>
@ThreadSafe
public class ServerStatusAfterSplit {
    @GuardedBy("users") public final Set<String> users;
    @GuardedBy("queries") public final Set<String> queries;

    public ServerStatusAfterSplit() {
        users = new HashSet<String>();
        queries = new HashSet<String>();
    }

    public void addUser(String u) {
        synchronized (users) {
            users.add(u);
        }
    }

    public void addQuery(String q) {
        synchronized (queries) {
            queries.add(q);
        }
    }

    public void removeUser(String u) {
        synchronized (users) {
            users.remove(u);
        }
    }

    public void removeQuery(String q) {
        synchronized (users) {
            queries.remove(q);
        }
    }
}
</pre>

如果在锁上存在适中而不是激烈的竞争时，通过将一个锁分解为两个锁，能最大限度地提升性能。
如果对竞争并不激烈的锁进行分解，那么在性能和吞吐量等方面带来的提升将非常有限，但是也会提高性能随着竞争提高而下降的拐点值。
对竞争适中的锁进行分解时，实际上是把这些锁转变为非竞争的锁，从而有效地提高性能和可变性。

#### 锁分段
在某些情况下，可以将锁分解技术进一步扩展为对一组独立对象上的锁进行分解，这种情况呗称为锁分段。例如ConcurrentHashMap

*锁分段技术*

<pre>
public class StripedMap {

    private static final int N_LOCKS = 16;
    private final Node[] buckets;
    private final Object[] locks;

    private static class Node {
        Node next;
        Object key;
        Object value;
    }
    
    public StripedMap(int numBuckets) {
        buckets = new Node[numBuckets];
        locks = new Object[N_LOCKS];
        for (int i = 0; i < N_LOCKS; i++)
            locks[i] = new Object();
    }

    private final int hash(Object key) {
        return Math.abs(key.hashCode() % buckets.length);
    }

    public Object get(Object key) {
        int hash = hash(key);
        synchronized (locks[hash % N_LOCKS]) {
            for (Node m = buckets[hash]; m != null; m = m.next)
                if (m.key.equals(key))
                    return m.value;
        }
        return null;
    }

    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            synchronized (locks[i % N_LOCKS]) {
                buckets[i] = null;
            }
        }
    }
}
</pre>

#### 避免热点区
热点域：比如ConcurrentHashMap.size()求元素个数时，是通过枚举每个segment.size累加的，如果你说想单独用一个size来保存元素个数，这样size(), isEmpty()这些方法就很简单了，但同样来一个问题，size的修改会很频繁，且须进行锁保护，反而又降低性能，这时的size 就是一个热点域。
#### 一些替代独占锁的方法
    ReadWriteLock
    原子变量

#### 监测CPU的利用率    

linux下可用vmstat或mpstat, windows下可用perfmon查看cpu状况。

cpu利用不充分的原因：

   1. 负载不充足。
   2. I/O密集。linux可用iostat, windows用perfmon。
   3. 外部限制。如数据库服务，web服务等。
   4. 锁竞争。可通过jstack等查看栈信息。 如果线程由于等待某个锁而被阻塞，那么在线程转储信息中将存在相应的栈帧，其中包含的信息形如“waiting to lock monitor...”。
   
#### 向对象池说“不”
对象分配操作的开销比同步的开销更低   
