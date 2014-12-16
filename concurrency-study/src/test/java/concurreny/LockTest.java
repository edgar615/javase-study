package concurreny;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2014/12/16.
 */
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
