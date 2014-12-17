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