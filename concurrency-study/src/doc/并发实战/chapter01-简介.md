### 安全性问题
<pre>
@NotThreadSafe
public class UnsafeSequence {

	private int value;

	public int getValue() {
        return value++;
	}
}
</pre>
<code>value ++</code>包含了三个独立的操作：

1. 读取value
2. value + 1
3. 将结果写入value

两个个线程可能同时执行了读操作，从而使它们得到相同的值，并都将这个值+1

    A value : 9 -------->   value + 1 : 10    --------> vlaue : 10

            B value : 9 -------->        value + 1 : 10             --------> vlaue : 10
            
**使用同步保证线程安全**

<pre>
@ThreadSafe
public class Sequence {

	@GuardedBy("this")
    private int value;

	public synchronized int getValue() {
		return value++;
	}
}
</pre>

### 活跃性问题

安全性的含义是“永远不发生糟糕的事情”，而活跃性则关注与另一个目标：“某件正确的事情最终会发生”。
当某个操作无法继续执行下去时，就会发生活跃性问题。
例如：线程A在等待线程B释放其持有的资源，而线程B永远都不释放该资源，那么A就会永久地等待下去。
多线程的活跃性问题包括：死锁、饥饿、活锁等

### 性能问题

在设计良好的并发应用程序中，线程能提升程序的性能，但无论如何，线程总会带来某种程度的运行时开销。
在多线程程序中，当线程调度器临时挂起活跃线程并转而运行另一个线程时，就会频繁地出现上下文切换操作，这种操作会带来极大的开销：保存和恢复执行上下文，丢失局部性，并且CPU时间将更多的花在线程调度而不是线程运行上。