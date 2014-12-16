Lock提供了一种无条件的、可轮询的、定时的以及可中断的锁获取操作，所有加锁和解锁的方法都是显式的。

*通过tryLock来避免锁顺序死锁*

<pre>
public class DeadlockAvoidance {
    private static Random rnd = new Random();

    public boolean transferMoney(Account fromAcct,
                                 Account toAcct,
                                 DollarAmount amount,
                                 long timeout,
                                 TimeUnit unit)
            throws InsufficientFundsException, InterruptedException {
        long fixedDelay = getFixedDelayComponentNanos(timeout, unit);
        long randMod = getRandomDelayModulusNanos(timeout, unit);
        long stopTime = System.nanoTime() + unit.toNanos(timeout);

        while (true) {
            if (fromAcct.lock.tryLock()) {
                try {
                    if (toAcct.lock.tryLock()) {
                        try {
                            if (fromAcct.getBalance().compareTo(amount) < 0)
                                throw new InsufficientFundsException();
                            else {
                                fromAcct.debit(amount);
                                toAcct.credit(amount);
                                return true;
                            }
                        } finally {
                            toAcct.lock.unlock();
                        }
                    }
                } finally {
                    fromAcct.lock.unlock();
                }
            }
            if (System.nanoTime() < stopTime)
                return false;
            TimeUnit.NANOSECONDS.sleep(fixedDelay + rnd.nextLong() % randMod);
        }
    }

    private static final int DELAY_FIXED = 1;
    private static final int DELAY_RANDOM = 2;

    static long getFixedDelayComponentNanos(long timeout, TimeUnit unit) {
        return DELAY_FIXED;
    }

    static long getRandomDelayModulusNanos(long timeout, TimeUnit unit) {
        return DELAY_RANDOM;
    }

    static class DollarAmount implements Comparable<DollarAmount> {
        public int compareTo(DollarAmount other) {
            return 0;
        }

        DollarAmount(int dollars) {
        }
    }

    class Account {
        public Lock lock;

        void debit(DollarAmount d) {
        }

        void credit(DollarAmount d) {
        }

        DollarAmount getBalance() {
            return null;
        }
    }

    class InsufficientFundsException extends Exception {
    }
}
</pre>

*带时间限制的加锁*

<pre>
public class TimedLocking {
    private Lock lock = new ReentrantLock();

    public boolean trySendOnSharedLine(String message,
                                       long timeout, TimeUnit unit)
            throws InterruptedException {
        long nanosToLock = unit.toNanos(timeout)
                - estimatedNanosToSend(message);
        if (!lock.tryLock(nanosToLock, TimeUnit.NANOSECONDS))
            return false;
        try {
            return sendOnSharedLine(message);
        } finally {
            lock.unlock();
        }
    }

    private boolean sendOnSharedLine(String message) {
        /* send something */
        return true;
    }

    long estimatedNanosToSend(String message) {
        return message.length();
    }
}
</pre>

#### 可中断的锁获取操作
lockInterruptibly方法能够在获得锁的同时保持对中断的响应。

*可中断的锁获取操作*

<pre>
public class InterruptibleLocking {
    private Lock lock = new ReentrantLock();

    public boolean sendOnSharedLine(String message)
            throws InterruptedException {
        lock.lockInterruptibly();
        try {
            return cancellableSendOnSharedLine(message);
        } finally {
            lock.unlock();
        }
    }

    private boolean cancellableSendOnSharedLine(String message) throws InterruptedException {
        /* send something */
        return true;
    }

}
</pre>

    Lock接口的 线程请求锁的 几个方法：
    
    lock(), 拿不到lock就不罢休，不然线程就一直block。 比较无赖的做法。
    tryLock()，马上返回，拿到lock就返回true，不然返回false。 比较潇洒的做法。
    带时间限制的tryLock()，拿不到lock，就等一段时间，超时返回false。比较聪明的做法。
    下面的lockInterruptibly()就稍微难理解一些。
    
    先说说线程的打扰机制，每个线程都有一个 打扰 标志。这里分两种情况，
    1. 线程在sleep或wait,join， 此时如果别的进程调用此进程的 interrupt（）方法，此线程会被唤醒并被要求处理InterruptedException；(thread在做IO操作时也可能有类似行为，见java thread api)
    2. 此线程在运行中， 则不会收到提醒。但是 此线程的 “打扰标志”会被设置， 可以通过isInterrupted()查看并 作出处理。
    
    lockInterruptibly()和上面的第一种情况是一样的， 线程在请求lock并被阻塞时，如果被interrupt，则“此线程会被唤醒并被要求处理InterruptedException”。


<pre>
public class LockTest {

    @Test
    public void testLock() throws InterruptedException {
        Lock lock = new ReentrantLock();
        lock.lock();
        Thread t = new Thread() {
            @Override
            public void run() {
                lock.lock();
                System.out.println("get lock");
            }
        };
        t.start();
        TimeUnit.SECONDS.sleep(2);
        t.interrupt();
        TimeUnit.SECONDS.sleep(10);
//        lock.unlock();
    }

    @Test
    public void testTryLock() throws InterruptedException {
        Lock lock = new ReentrantLock();
        lock.lock();
        Thread t = new Thread() {
            @Override
            public void run() {
                if (lock.tryLock()) {
                    System.out.println("get lock");
                } else {
                    System.out.println("can not get lock");
                }
            }
        };
        t.start();
        TimeUnit.SECONDS.sleep(2);
        t.interrupt();
        TimeUnit.SECONDS.sleep(10);
//        lock.unlock();
    }

    @Test
    public void testTryLockTime() throws InterruptedException {
        Lock lock = new ReentrantLock();
        lock.lock();
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    if (lock.tryLock(1, TimeUnit.SECONDS)) {
                        System.out.println("get lock");
                    } else {
                        System.out.println("can not get lock");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        TimeUnit.SECONDS.sleep(2);
        t.interrupt();
        TimeUnit.SECONDS.sleep(10);
//        lock.unlock();
    }

    @Test
    public void testLockInterrupt() throws InterruptedException {
        Lock lock = new ReentrantLock();
        lock.lock();
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    lock.lockInterruptibly();
                    System.out.println("get lock");
                } catch (InterruptedException e) {
                    System.out.println("Interrupted");
                }
            }
        };
        t.start();
        TimeUnit.SECONDS.sleep(2);
        t.interrupt();
        TimeUnit.SECONDS.sleep(10);
//        lock.unlock();
    }
}
</pre>

### 性能考虑因素

### 公平性
在公平的锁上，线程按照他们发出请求的顺序获取锁，但在非公平锁上，则允许‘插队’：当一个线程请求非公平锁时，如果在发出请求的同时该锁变成可用状态，那么这个线程会跳过队列中所有的等待线程而获得锁。非公平的ReentrantLock 并不提倡插队行为，但是无法防止某个线程在合适的时候进行插队。
在公平的锁中，如果有另一个线程持有锁或者有其他线程在等待队列中等待这个锁，那么新发出的请求的线程将被放入到队列中。而非公平锁上，只有当锁被某个线程持有时，新发出请求的线程才会被放入队列中。

在大多数情况下，非公平锁的性能要高于公平锁的性能。（当执行加锁操作时，公平性将由于在挂起线程和恢复线程时存在的开销而极大地降低性能。）

### synchronized和ReentrantLock之间的选择
仅当内置锁不能满足需求时，才可以考虑使用ReentrantLock。当需要一些高级功能时踩应该使用ReentrantLock：可定时的、可轮询的与可中断的锁获取操作，公平队列、以及非块结构的锁。否则还是应该优先使用synchronized。

### 读/写锁
读/写锁：一个资源可以被多个读操作访问，或者一个写操作访问，但两者不能同时进行。

对于在多处理系统上被频繁读取的数据结构，读-写锁能够提高性能。而在其他情况下，读-写锁的性能比独占锁的性能要略差一些。

*用读/写锁来包装Map*

<pre>
public class ReadWriteMap <K,V> {
    private final Map<K, V> map;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock r = lock.readLock();
    private final Lock w = lock.writeLock();

    public ReadWriteMap(Map<K, V> map) {
        this.map = map;
    }

    public V put(K key, V value) {
        w.lock();
        try {
            return map.put(key, value);
        } finally {
            w.unlock();
        }
    }

    public V remove(Object key) {
        w.lock();
        try {
            return map.remove(key);
        } finally {
            w.unlock();
        }
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        w.lock();
        try {
            map.putAll(m);
        } finally {
            w.unlock();
        }
    }

    public void clear() {
        w.lock();
        try {
            map.clear();
        } finally {
            w.unlock();
        }
    }

    public V get(Object key) {
        r.lock();
        try {
            return map.get(key);
        } finally {
            r.unlock();
        }
    }

    public int size() {
        r.lock();
        try {
            return map.size();
        } finally {
            r.unlock();
        }
    }

    public boolean isEmpty() {
        r.lock();
        try {
            return map.isEmpty();
        } finally {
            r.unlock();
        }
    }

    public boolean containsKey(Object key) {
        r.lock();
        try {
            return map.containsKey(key);
        } finally {
            r.unlock();
        }
    }

    public boolean containsValue(Object value) {
        r.lock();
        try {
            return map.containsValue(value);
        } finally {
            r.unlock();
        }
    }
}
</pre>