在编译器中生成的指令顺序，可以与源代码中的顺序不同，此外编译器还会把变量保存在寄存器而不是内存中；
处理器可以采用乱序或并行等方式来执行指令；
缓存可能会改变将写入变量提交到主内存的顺序；
而且，保存在处理器本地缓存中的值，对于其他处理器是不可见的。
这些因素都会使得一个线程无法看到变量的最新值，并且会导致其他线程中的内存操作似乎在乱序执行——如果没有使用正确的同步。

JAVA语言规范要求JVM在线程中维护一种类似串行的语义：只要程序的最终结果与在严格串行环境中执行的结果相同，那么上述所有操作都是允许的。

只有当多个线程要共享数据时，才必须协调它们之间的操作，并且JVM依赖程序通过同步操作来找出这些协调操作将在何时发生。

#### 平台的内存模型

#### 重排序
在没有充分同步的程序中，如果调度器采用不恰当的方式来交替执行不同的线程操作，那么将导致不正确的结果。
更糟的是，JMM还使得不同线程看到到操作执行顺序是不同的，从而导致在缺乏同步的情况下，要推断操作的执行顺序将变得更加复杂。
各种使操作延迟或者看似乱序执行的不同原因，都可以归为重排序。

<pre>
</pre>

很容易想到这段代码的运行结果可能为(1,0)、(0,1)或(1,1)，因为线程one可以在线程two开始之前就执行完了，也有可能反之，甚至有可能二者的指令是同时或交替执行的。

然而，这段代码的执行结果也可能是(0,0). 因为，在实际运行时，代码指令可能并不是严格按照代码语句顺序执行的。得到(0,0)结果的语句执行过程，如下图所示。值得注意的是，a=1和x=b这两个语句的赋值操作的顺序被颠倒了，或者说，发生了指令“重排序”(reordering)。

#### java内存模型
Java内存模型是通过各种操作来定义的，包括对变量的读/写操作，监视器的加锁和释放操作，以及线程的启动和合并操作。
JMM为程序中所有的操作定义了一个偏序关系，称之为Happens-Before。
要想保证执行操作B的线程看到操作A的结果（无论A和B是否在同一个线程中执行），那么在A和B之间必须满足Happens-Before关系。
如果两个操作之间缺乏Happens-Before关系，那么JVM可以对它们任意重排序。

Happens-Before的规则包括：

    程序顺序规则。如果程序中操作A在操作B之前，那么在线程中A操作将在B操作之前执行。
    监视器锁规则。在监视器锁上的解锁操作必须在同一个监视器锁上的加锁操作之前执行。
    volatile规则。对volatile变量的写入操作必须在对该变量的读操作之前执行。
    线程启动规则。在线程上对Thread.start的调用必须在该线程中执行任何操作之前执行。
    线程结束规则。线程中的任何操作都必须在其他线程检测到该线程已经结束之前执行，或者从Thread.join成功返回，或者在调用Thread.isAlive时返回false
    中断规则。当一个线程在另一个线程上调用interrupt时，必须在被中断线程检测到interrupt调用之前执行。
    终结器规则。对象的构造函数必须在启动该对象的终结器之前执行完成。
    传递性。如果操作A在操作B之前执行，并且操作B在操作C之前执行，那么操作A必须在操作C之前执行。
    
#### 借助同步
    
### 发布
#### 不安全的发布
    
*不安全的延迟初始化*

<pre>
@NotThreadSafe
public class UnsafeLazyInitialization {
    private static Resource resource;

    public static Resource getInstance() {
        if (resource == null)
            resource = new Resource(); // unsafe publication
        return resource;
    }

    static class Resource {
    }
}
</pre>    

线程A写入resource的操作与线程B读取resource的操作之间不存在Happens-Before关系。
在发布对象时存在数据竞争问题，因此B并不一定能看到Resource的正确状态。

#### 安全的发布

*线程安全的延迟初始化*

<pre>
@ThreadSafe
public class SafeLazyInitialization {
    private static Resource resource;

    public synchronized static Resource getInstance() {
        if (resource == null)
            resource = new Resource();
        return resource;
    }

    static class Resource {
    }
}
</pre>

*提前初始化*

<pre>
@ThreadSafe
  public class EagerInitialization {
    private static Resource resource = new Resource();

    public static Resource getResource() {
        return resource;
    }

    static class Resource {
    }
}

</pre>

*延迟初始化的占位类模式*

<pre>
@ThreadSafe
public class ResourceFactory {
    private static class ResourceHolder {
        public static Resource resource = new Resource();
    }

    public static Resource getResource() {
        return ResourceFactory.ResourceHolder.resource;
    }

    static class Resource {
    }
}
</pre>

JVM将推迟ResourceHolder的初始化操作，直到开始使用这个类时踩初始化。

*双重检查加锁*

<pre>
@NotThreadSafe
public class DoubleCheckedLocking {
    private static Resource resource;

    public static Resource getInstance() {
        if (resource == null) {
            synchronized (DoubleCheckedLocking.class) {
                if (resource == null)
                    resource = new Resource();
            }
        }
        return resource;
    }

    static class Resource {

    }
}
</pre>

上述代码线程可能看到一个仅被部分构造的Resource。

### 初始化过程中的安全性

对于含有final域的对象，初始化安全性可以防止对对象的初始引用被重排序到构造过程之前。
当构造函数完成时，构造函数对final域的所有写入操作，以及对通过这些域可以到达的任何变量的写入操作，都将被“冻结”，
并且任何获得该对象引用的线程都至少能确保看到被冻结的值。
对于通过final域可到达的初始变量的写入操作，将不会与构造过程后的操作一起被重排序。

<pre>
@ThreadSafe
public class SafeStates {
    private final Map<String, String> states;

    public SafeStates() {
        states = new HashMap<String, String>();
        states.put("alaska", "AK");
        states.put("alabama", "AL");
        /*...*/
        states.put("wyoming", "WY");
    }

    public String getAbbreviation(String s) {
        return states.get(s);
    }
}
</pre>