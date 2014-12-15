### 死锁
当一个线程永远地持有一个锁，并且其他线程都尝试获得这个锁时，那么它们将永远被阻塞。在线程A持有锁L并想获得锁M的同时，线程B持有锁M并尝试获得锁L，那么这两个线程将永远地等待下去。这种情况就是最简单的死锁形式，其中多个线程由于存在环路的锁依赖关系而永远地等待下去。

#### 锁顺序死锁

*简单的锁顺序死锁*

<pre>
public class LeftRightDeadlock {
    
    private final Object left = new Object();
    private final Object right = new Object();
    
    public void leftRight() {
        synchronized (left) {
            synchronized (right) {
                doSomething();
            }
        }
    }

    public void rightLeft() {
        synchronized (right) {
            synchronized (left) {
                doSomething();
            }
        }
    }

    private void doSomething() {

    }
}
</pre>

如果所有线程以固定的顺序来获得锁，那么在程序中就不会出现锁顺序死锁问题。

#### 动态的锁顺序死锁

<pre>
public class DynamicOrderDeadlock {
    // Warning: deadlock-prone!
    public static void transferMoney(Account fromAccount,
                                     Account toAccount,
                                     DollarAmount amount)
            throws InsufficientFundsException {
        synchronized (fromAccount) {
            synchronized (toAccount) {
                if (fromAccount.getBalance().compareTo(amount) < 0)
                    throw new InsufficientFundsException();
                else {
                    fromAccount.debit(amount);
                    toAccount.credit(amount);
                }
            }
        }
    }

    static class DollarAmount implements Comparable<DollarAmount> {
        // Needs implementation

        public DollarAmount(int amount) {
        }

        public DollarAmount add(DollarAmount d) {
            return null;
        }

        public DollarAmount subtract(DollarAmount d) {
            return null;
        }

        public int compareTo(DollarAmount dollarAmount) {
            return 0;
        }
    }

    static class Account {
        private DollarAmount balance;
        private final int acctNo;
        private static final AtomicInteger sequence = new AtomicInteger();

        public Account() {
            acctNo = sequence.incrementAndGet();
        }

        void debit(DollarAmount d) {
            balance = balance.subtract(d);
        }

        void credit(DollarAmount d) {
            balance = balance.add(d);
        }

        DollarAmount getBalance() {
            return balance;
        }

        int getAcctNo() {
            return acctNo;
        }
    }

    static class InsufficientFundsException extends Exception {
    }
}
</pre>

如果2个线程以如下的方式调用transferMoney方法:

    A: transferMoney(myAccount, yourAccount, 10); 
    B: transferMoney(yourAccount, myAccount, 20);

就可能发生死锁。

如果执行时序不当，那么A可能获得myAccount的锁并等待yourAccount的锁，然而B此时持有yourAccount的锁，并正在等待myAccount的锁。

由于我们无法控制参数的顺序，因此要解决这个问题，必须定义锁的顺序，并在整个应用程序中都按照这个顺序来获取锁。

在制定锁的顺序时，可以使用<code>System.identityHashCode</code>方法，该方法返回由Object.hashcode返回的值。

*通过锁顺序来避免死锁*

<pre>
public class InduceLockOrder {

    private static final Object tieLock = new Object();

    public static void transferMoney(Account fromAccount,
                                     Account toAccount,
                                     DollarAmount amount)
            throws InsufficientFundsException {

        class Helper {
            public void transfer() throws InsufficientFundsException {
                if (fromAccount.getBalance().compareTo(amount) < 0)
                    throw new InsufficientFundsException();
                else {
                    fromAccount.debit(amount);
                    toAccount.credit(amount);
                }
            }
        }

        int fromHash = System.identityHashCode(fromAccount);
        int toHash = System.identityHashCode(toAccount);
        if (fromHash < toHash) {
            synchronized (fromAccount) {
                synchronized (toAccount) {
                    new Helper().transfer();
                }
            }
        } else if (fromHash > toHash) {
            synchronized (toAccount) {
                synchronized (fromAccount) {
                    new Helper().transfer();
                }
            }
        } else {
            synchronized (tieLock) {
                synchronized (fromAccount) {
                    synchronized (toAccount) {
                        new Helper().transfer();
                    }
                }
            }
        }

    }

}
</pre>

如果在Account中包含一个唯一的、不可变的，并且具备可比性的键值，例如账号，可以通过键值对对象进行排序，因此不需要使用“加时赛”锁(tieLock)。

#### 在协作对象之间发生的死锁

*在相互协作对象之间的锁顺序死锁*

<pre>
public class CooperatingDeadlock {
    // Warning: deadlock-prone!
    class Taxi {
        @GuardedBy("this") private Point location, destination;
        private final Dispatcher dispatcher;

        public Taxi(Dispatcher dispatcher) {
            this.dispatcher = dispatcher;
        }

        public synchronized Point getLocation() {
            return location;
        }

        public synchronized void setLocation(Point location) {
            this.location = location;
            if (location.equals(destination))
                dispatcher.notifyAvailable(this);
        }

        public synchronized Point getDestination() {
            return destination;
        }

        public synchronized void setDestination(Point destination) {
            this.destination = destination;
        }
    }

    class Dispatcher {
        @GuardedBy("this") private final Set<Taxi> taxis;
        @GuardedBy("this") private final Set<Taxi> availableTaxis;

        public Dispatcher() {
            taxis = new HashSet<Taxi>();
            availableTaxis = new HashSet<Taxi>();
        }

        public synchronized void notifyAvailable(Taxi taxi) {
            availableTaxis.add(taxi);
        }

        public synchronized Image getImage() {
            Image image = new Image();
            for (Taxi t : taxis)
                image.drawMarker(t.getLocation());
            return image;
        }
    }

    class Image {
        public void drawMarker(Point p) {
        }
    }
}
</pre>

**如果在持有锁的情况下调用某个外部方法，那么就需要警惕死锁**

#### 开放调用

如果在调用某个方法时不需要持有锁，那么这种调用被称为开放调用。

*通过公开调用来避免在相互协作的对象之间产生死锁*

<pre>
class CooperatingNoDeadlock {
    @ThreadSafe
    class Taxi {
        @GuardedBy("this") private Point location, destination;
        private final Dispatcher dispatcher;

        public Taxi(Dispatcher dispatcher) {
            this.dispatcher = dispatcher;
        }

        public synchronized Point getLocation() {
            return location;
        }

        public synchronized void setLocation(Point location) {
            boolean reachedDestination;
            synchronized (this) {
                this.location = location;
                reachedDestination = location.equals(destination);
            }
            if (reachedDestination)
                dispatcher.notifyAvailable(this);
        }

        public synchronized Point getDestination() {
            return destination;
        }

        public synchronized void setDestination(Point destination) {
            this.destination = destination;
        }
    }

    @ThreadSafe
    class Dispatcher {
        @GuardedBy("this") private final Set<Taxi> taxis;
        @GuardedBy("this") private final Set<Taxi> availableTaxis;

        public Dispatcher() {
            taxis = new HashSet<Taxi>();
            availableTaxis = new HashSet<Taxi>();
        }

        public synchronized void notifyAvailable(Taxi taxi) {
            availableTaxis.add(taxi);
        }

        public Image getImage() {
            Set<Taxi> copy;
            synchronized (this) {
                copy = new HashSet<Taxi>(taxis);
            }
            Image image = new Image();
            for (Taxi t : copy)
                image.drawMarker(t.getLocation());
            return image;
        }
    }

    class Image {
        public void drawMarker(Point p) {
        }
    }

}
</pre>

在程序中应尽量使用开放调用。与那些在持有锁时调用外部方法的程序相比，更易于对依赖于开放调用的程序进行死锁分析。

#### 资源死锁
当多个线程在相同的资源集合上等待时，也会发生死锁。

**有界线程池/资源池与相互依赖的任务不能一起使用**

### 死锁的避免与诊断
如果避免获取多个锁，那么在设计时必须考虑锁的顺序，尽量减少潜在的加锁交互数量，将获取锁时需要遵循的协议写入正式文档并始终遵循这些协议。

#### 支持定时的锁
可以使用Lock类的tryLock方法来代替内置锁机制。

#### 通过线程转储信息来分析死锁

### 其他活跃性危险

#### 饥饿
避免使用线程优先级，因为这会增加平台依赖性，并可能导致活跃性问题。在大多数并发应用程序中，都可以使用默认的线程优先级。

#### 糟糕的响应性

#### 活锁

活锁是另一种形式的活跃性问题，该问题尽管不用阻塞线程，但也不能继续执行，因为线程将不断重复执行相同的操作。而且总会失败。

活锁通常发生在处理事务消息的应用程序中，如果不能成功的处理某个消息，那么消息处理机制将回滚整个事务。并将它重新放到队列的开头。

当多个相互协作的线程都对彼此进行响应从而修改各自的状态，并使得任何一个线程都无法继续执行时，就发生了活锁。

解决问题的办法是在重试机制中引入随机性。如，两台机器尝试使用相同的载波（就是频率，如果相同的话会发生混叠，使传输信号失真）来发送数据包，那么这些数据包就会发生冲突。并又双双重试。引入随机的概念后，让它们在等待随机的时间段后再重试。以太协议定义了重复发生冲突时采用指数方式回退机制，从而降低在多台存在冲突的机器之间发生拥塞和反复失败的风险。

在并发应用中，我们可以通过让程序等待随机长度的时间来避免活锁的发生。


