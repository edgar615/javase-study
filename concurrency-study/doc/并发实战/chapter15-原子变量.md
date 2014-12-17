### 硬件对并发的支持
#### 比较并交换
CAS包含了3个操作数——需要读写的内存位置V、进行比较的值A和拟写入的新值B。
当前仅当V的值等于A时，CAS才会通过原子方式用新值B来更新V的值，否则不会执行任何操作。无论位置V的值是否等于A，都将返回V原有的值。

CAS是一项乐观的技术，它希望能够成功地执行更新操作，并且如果有另一个线程在最近一次检查后更新了该变量，那么CAS能检测到这个错误。

*模拟CAS*

<pre>
public class SimulatedCAS {
    private int value;

    public synchronized int get() {
        return value;
    }

    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        if (oldValue == expectedValue) {
            value = newValue;
        }
        return oldValue;
    }

    public synchronized boolean compareAndSet(int expectedValue, int newValue) {
        return (expectedValue == compareAndSwap(expectedValue, newValue));
    }
}
</pre>

CAS的典型使用模式是：首先从V中读取值A，并根据A计算新值B，任何再通过CAS以原子方式将V中的值由A变成B（只要在这期间没有任何线程将V的值修改为其他值）。
由于CAS能检测到来自其他线程的干扰，因此即使不使用锁也能够实现原子的读-改-写操作序列。

#### 非阻塞的计数器

*基于CAS实现的非阻塞计数器*

<pre>
public class CasCounter {

    private SimulatedCAS value;

    public int getValue() {
        return value.get();
    }

    public int increment() {
        int v;
        do {
            v = value.get();
        } while (v != value.compareAndSwap(v, v + 1));
        return v + 1;

    }
}
</pre>

### 原子变量类

*通过CAS来维持包含多个变量的不变性条件*

<pre>
public class CasNumberRange {

    @Immutable
    private static class IntPair {
        // INVARIANT: lower <= upper
        final int lower;
        final int upper;

        public IntPair(int lower, int upper) {
            this.lower = lower;
            this.upper = upper;
        }
    }

    private final AtomicReference<IntPair> values = new AtomicReference<>(new IntPair(0, 0));

    public int getLower() {
        return values.get().lower;
    }

    public int getUpper() {
        return values.get().upper;
    }

    public void setLower(int i) {
        while (true) {
            IntPair oldv = values.get();
            if (i > oldv.upper) {
                throw new IllegalArgumentException("Can't set lower to " + i + " > upper");
            }
            IntPair newv = new IntPair(i, oldv.upper);
            if (values.compareAndSet(oldv, newv)) {
                return;
            }
        }
    }

    public void setUpper(int i) {
        while (true) {
            IntPair oldv = values.get();
            if (i < oldv.lower) {
                throw new IllegalArgumentException("Can't set upper to " + i + " < lower");
            }
            IntPair newv = new IntPair(oldv.lower, i);
            if (values.compareAndSet(oldv, newv)) {
                return;
            }
        }
    }

}
</pre>

*基于Lock的随机数生成器*

<pre>
@ThreadSafe
public class ReentrantLockPseudoRandom extends PseudoRandom {
    private final Lock lock = new ReentrantLock(false);
    private int seed;

    ReentrantLockPseudoRandom(int seed) {
        this.seed = seed;
    }

    public int nextInt(int n) {
        lock.lock();
        try {
            int s = seed;
            seed = calculateNext(s);
            int remainder = s % n;
            return remainder > 0 ? remainder : remainder + n;
        } finally {
            lock.unlock();
        }
    }
}
</pre>

*基于AtomicInteger的随机数生成器*

<pre>
@ThreadSafe
public class AtomicPseudoRandom extends PseudoRandom {
    private AtomicInteger seed;

    AtomicPseudoRandom(int seed) {
        this.seed = new AtomicInteger(seed);
    }

    public int nextInt(int n) {
        while (true) {
            int s = seed.get();
            int nextSeed = calculateNext(s);
            if (seed.compareAndSet(s, nextSeed)) {
                int remainder = s % n;
                return remainder > 0 ? remainder : remainder + n;
            }
        }
    }
}
</pre>

在实际情况中，原子变量在可伸缩性上要高于锁，因为在应对常见的竞争程度时，原子变量的效率会更高。

在中低程度的竞争下，原子变量能提供更高的可伸缩性，而在高程度的竞争下，锁能够更有效地避免竞争。

如果能够避免使用共享状态，那么开销将会更小。我们可以通过提高处理竞争的效率来提高可伸缩性，但只要完全消除竞争，才能实现真正的可伸缩性。

### 非阻塞算法
在基于锁的算法中可能会发生各种活跃性故障。如果线程在持有锁时由于阻塞I/O，内存页缺失或其他延迟而导致推迟执行，那么很可能所有线程都不能继续执行下去。
如果子啊某种算法中，一个线程的失败或挂起不会导致其他线程也失败或挂起，那么这种算法就被称为非阻塞算法。
如果在算法的每个步骤中都存在某个线程能够执行下去，那么这种算法也被称为无锁算法。
如果在算法中仅将CAS用于协调线程之间的操作，并且能正确地实现，那么它既是一种无阻塞算法，又是一种无锁算法。
无竞争的CAS通常都能执行下去。在非阻塞算法中通常不会出现死锁和优先级反转问题（但可能出现饥饿和活锁问题，因为在算法中会反复地重试）。

#### 非阻塞的栈

*非阻塞栈*

<pre>
@ThreadSafe
public class ConcurrentStack<E> {

    AtomicReference<Node<E>> top = new AtomicReference<>();

    public void push(E item) {
        Node<E> newHead = new Node<>(item);
        Node<E> oldHead;
        do {
            oldHead = top.get();
            newHead.next = oldHead;
        } while (!top.compareAndSet(oldHead, newHead));
    }

    public E pop() {
        Node<E> oldHead;
        Node<E> newHead;
        do {
            oldHead = top.get();
            if (oldHead == null) {
                return null;
            }
            newHead = oldHead.next;
        } while (!top.compareAndSet(oldHead, newHead));
        return oldHead.item;
    }

    private static class Node<E> {
        public final E item;
        public Node<E> next;

        public Node(E item) {
            this.item = item;
        }
    }
}
</pre>

非阻塞算法的特性：某些工作的完成具有不确定性，必须重新执行。

#### 非阻塞的链表
构建非阻塞算法的技巧在于：将执行原子修改的范围缩小到单个变量上。

<pre>
@ThreadSafe
public class LinkedQueue <E> {

    private static class Node <E> {
        final E item;
        final AtomicReference<Node<E>> next;

        public Node(E item, LinkedQueue.Node<E> next) {
            this.item = item;
            this.next = new AtomicReference<LinkedQueue.Node<E>>(next);
        }
    }

    private final LinkedQueue.Node<E> dummy = new LinkedQueue.Node<E>(null, null);
    private final AtomicReference<LinkedQueue.Node<E>> head
            = new AtomicReference<LinkedQueue.Node<E>>(dummy);
    private final AtomicReference<LinkedQueue.Node<E>> tail
            = new AtomicReference<LinkedQueue.Node<E>>(dummy);

    public boolean put(E item) {
        LinkedQueue.Node<E> newNode = new LinkedQueue.Node<E>(item, null);
        while (true) {
            //尾节点
            LinkedQueue.Node<E> curTail = tail.get();
            LinkedQueue.Node<E> tailNext = curTail.next.get();
            if (curTail == tail.get()) { //检查队列是否处于中间状态（有无另一个线程正在插入元素）
                if (tailNext != null) {
                    // tailNext不为null，表示队列已经被修改；队列处于中间状态，将尾节点推进到tailNext
                    tail.compareAndSet(curTail, tailNext);
                } else {
                    // 队列处于稳定状态，增加新节点
                    if (curTail.next.compareAndSet(null, newNode)) {
                        // 将尾节点推进到newNode
                        tail.compareAndSet(curTail, newNode);
                        return true;
                    }
                }
            }
        }
    }
}
</pre>

#### 原子的域更新器
AtomicReferenceFieldUpdater

原子的域更新器类表示现有的volatile域的一种基于反射的“视图”，从而能够在已有的volatile域上使用CAS。

几乎在所有情况下，普通的原子变量的性能都很不错，只有在很少的情况下才需要使用原子的域更新器。

#### ABA问题
ABA问题是一种异常现象：如果在算法中的节点可以被循环使用，那么在使用“比较并交换”指令时就可能出现这个问题（如果在没有垃圾回收机制的环境 中）。在CAS操作中将判断“V的值是否仍然为A？”，并且如果是的话就继续执行更新操作。在大多数情况下，这种判断是足够的。然而，有时候还需要知道 “自从上次看到V的值为A以来，这个值是否发生了变化？”在某些算法中，如果V值首先由A编程B,在由B编程A，那么仍然被认为发生了变化，并需要重新执 行算法中的某些步骤。

 

如果在算法中采用自己的方式来管理节点对象的内存，那么可能出现ABA问题。在这种情况下，即使链表的头结点仍然只想之前观察到的节点，那么也不足 以说明链表的内容没有发生变化。如果通过垃圾回收器来管理链表节点仍然无法避免ABA问题，那么还有一个相对简单的解决方法：不是只是更新某个引用的值， 而是更新两个值，包含一个引用和一个版本号。即使这个值由A变成B，然后又变为A，版本号也将是不同的。AtomicStampedReference以 及AtomicMarkableReference支持在两个变量上执行原子的条件更新。AtomicStampedReference将更新一个“对象 —-引用”二元组，通过在引用上加上“版本号”，从而避免ABA问题。类似地，AtomicMarkableReference将更新一个“对象引用—- 布尔值”二元组，在某些算法中将通过这种二元组使节点保存在链表中同时又将其标记为“已删除节点”。