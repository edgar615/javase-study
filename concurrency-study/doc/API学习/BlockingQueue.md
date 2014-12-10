BlockingQueue 方法以四种形式出现，对于不能立即满足但可能在将来某一时刻可以满足的操作，这四种形式的处理方式不同：第一种是抛出一个异常，第二种是返回一个特殊值（null 或 false，具体取决于操作），第三种是在操作可以成功前，无限期地阻塞当前线程，第四种是在放弃前只在给定的最大时间限制内阻塞。下表中总结了这些方法：

<table>
    <tr>
        <th></th>
        <th>抛出异常</th>
        <th>特殊值</th>
        <th>阻塞</th>
        <th>超时</th>
    </tr>
    <tr>
        <th>插入</th>
        <td>add(e)</td>
        <td>offer(e)</td>
        <td>put(e)</td>
        <td>offer(e, time, unit)</td>
    </tr>
    <tr>
        <th>移除</th>
        <td>remove()</td>
        <td>poll()</td>
        <td>take()</td>
        <td>poll(time, unit)</td>
    </tr>
    <tr>
        <th>检查</th>
        <td>element()</td>
        <td>peek()</td>
        <td>不可用</td>
        <td>不可用</td>
    </tr>
</table>

示例

<pre>
public class Producer implements Runnable {

    private BlockingQueue queue;

    public Producer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            queue.put("1");
            System.out.println("put 1");
            Thread.sleep(1000);
            queue.put("2");
            System.out.println("put 2");
            Thread.sleep(1000);
            queue.put("3");
            System.out.println("put 3");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
</pre>

<pre>
public class Consumer implements Runnable {

    private BlockingQueue queue;

    public Consumer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            System.out.println(queue.take());
            Thread.sleep(1500);
            System.out.println(queue.take());
            Thread.sleep(1500);
            System.out.println(queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
</pre>

<pre>
BlockingQueue<String> queue = new ArrayBlockingQueue<String>(1);
Producer producer = new Producer(queue);
Consumer consumer = new Consumer(queue);
new Thread(producer).start();
new Thread(consumer).start();

TimeUnit.SECONDS.sleep(5);
</pre>

JDK提供了5个BlockingQueue的具体实现

- ArrayBlockingQueue
- DelayQueue
- LinkedBlockingQueue
- PriorityBlockingQueue
- SynchronousQueue

#### ArrayBlockingQueue
 一个由数组支持的有界阻塞队列。此队列按 FIFO（先进先出）原则对元素进行排序。队列的头部 是在队列中存在时间最长的元素。队列的尾部 是在队列中存在时间最短的元素。新元素插入到队列的尾部，队列获取操作则是从队列头部开始获得元素。

这是一个典型的“有界缓存区”，固定大小的数组在其中保持生产者插入的元素和使用者提取的元素。一旦创建了这样的缓存区，就不能再增加其容量。试图向已满队列中放入元素会导致操作受阻塞；试图从空队列中提取元素将导致类似阻塞。

此类支持对等待的生产者线程和使用者线程进行排序的可选公平策略。默认情况下，不保证是这种排序。然而，通过将公平性

#### DelayQueue
Delayed 元素的一个无界阻塞队列，只有在延迟期满时才能从中提取元素。该队列的头部 是延迟期满后保存时间最长的 Delayed 元素。如果延迟都还没有期满，则队列没有头部，并且 poll 将返回 null。 **当一个元素的 getDelay(TimeUnit.NANOSECONDS) 方法返回一个小于等于 0 的值时，将发生到期。** 即使无法使用 take 或 poll 移除未到期的元素，也不会将这些元素作为正常元素对待。例如，size 方法同时返回到期和未到期元素的计数。此队列不允许使用 null 元素。 

<pre>
public class DelayedElement<T> implements Delayed {

    private final long origin;

    private final long delay;

    private final T element;

    public DelayedElement(T element, long delay) {
        this.element = element;
        this.delay = delay;
        this.origin = System.currentTimeMillis();
    }

    /**
     * 还有多久过期
     *
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(delay - (System.currentTimeMillis() - origin), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (o == this) {
            return 0;
        }
        if (o instanceof DelayedElement) {
            long diff = origin + delay - ((DelayedElement) o).delay - ((DelayedElement) o).origin;
            return ((diff == 0) ? 0 : ((diff < 0) ? -1 : 1));
        }
        long d = (getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        return ((d == 0) ? 0 : ((d < 0) ? -1 : 1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DelayedElement that = (DelayedElement) o;

        if (element != null ? !element.equals(that.element) : that.element != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return element != null ? element.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "{" +
                "data='" + element + '\'' +
                ", startTime=" + origin +
                ", delay=" + delay +
                '}';
    }
}
</pre>
	 	
<pre>
public class DelayQueueProducer implements Runnable {

    private final BlockingQueue<Delayed> queue;

    private final Random random = new Random();

    public DelayQueueProducer(BlockingQueue<Delayed> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i ++) {
            int delay = random.nextInt(10000);
            Delayed delayed = new DelayedElement<String>(UUID.randomUUID().toString(), delay);
            System.out.printf("Put object = %s%n", delayed);
            try {
                queue.put(delayed);
//                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
</pre>
	 	 	 	 	 	
<pre>
public class DelayQueueConsumer implements Runnable {

    private final BlockingQueue<Delayed> queue;

    public DelayQueueConsumer(BlockingQueue<Delayed> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Delayed delayed = queue.take();
                System.out.printf("[%s] - Take object = %s%n",
                        Thread.currentThread().getName(), delayed);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
</pre>	
 	 	 	 	 	
<pre>
BlockingQueue queue = new DelayQueue();

Thread t1 = new Thread(new DelayQueueProducer(queue), "producer");
Thread t2 = new Thread(new DelayQueueConsumer(queue), "consumer");
t1.start();
t2.start();
</pre> 	 	 	 	 	

#### LinkedBlockingQueue 
一个基于已链接节点的、范围任意的 blocking queue。此队列按 FIFO（先进先出）排序元素。队列的头部 是在队列中时间最长的元素。队列的尾部 是在队列中时间最短的元素。新元素插入到队列的尾部，并且队列获取操作会获得位于队列头部的元素。链接队列的吞吐量通常要高于基于数组的队列，但是在大多数并发应用程序中，其可预知的性能要低	 	 	 	
 	 	 	 	
#### PriorityBlockingQueue 
 	 	 	 	
一个无界阻塞队列，它使用与类 PriorityQueue 相同的顺序规则，并且提供了阻塞获取操作。虽然此队列逻辑上是无界的，但是资源被耗尽时试图执行 add 操作也将失败（导致 OutOfMemoryError）。此类不允许使用 null 元素。依赖自然顺序的优先级队列也不允许插入不可比较的对象（这样做会导致抛出 ClassCastException）。
  	 	 	 	
#### SynchronousQueue 
  	 	 	 	
 一种阻塞队列，其中每个插入操作必须等待另一个线程的对应移除操作 ，反之亦然。同步队列没有任何内部容量，甚至连一个队列的容量都没有。不能在同步队列上进行 peek，因为仅在试图要移除元素时，该元素才存在；除非另一个线程试图移除某个元素，否则也不能（使用任何方法）插入元素；也不能迭代队列，因为其中没有元素可用于迭代。队列的头 是尝试添加到队列中的首个已排队插入线程的元素；如果没有这样的已排队线程，则没有可用于移除的元素并且 poll() 将会返回 null。对于其他 Collection 方法（例如 contains），SynchronousQueue 作为一个空 collection。此队列不允许 null 元素。

同步队列类似于 CSP 和 Ada 中使用的 rendezvous 信道。它非常适合于传递性设计，在这种设计中，在一个线程中运行的对象要将某些信息、事件或任务传递给在另一个线程中运行的对象，它就必须与该对象同步。
> One reason to use SynchronousQueue is to improve application performance. If you must have a hand-off between threads, you will need some synchronization object. If you can satisfy the conditions required for its use, SynchronousQueue is the fastest synchronization object I have found

 	 	 	 	