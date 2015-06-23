package concurrent;

import com.google.common.util.concurrent.CycleDetectingLockFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * Created by Administrator on 2015/6/23.
 */
public class CycleDetectingLockOrderingExample {
    public static void main(String[] args) {
        //死锁
//        Lock lockA = new ReentrantLock();
//        Lock lockB = new ReentrantLock();
        CycleDetectingLockFactory.WithExplicitOrdering<MyLockOrder> factory =
           CycleDetectingLockFactory.newInstanceWithExplicitOrdering(MyLockOrder.class, CycleDetectingLockFactory.Policies.WARN);
//        CycleDetectingLockFactory factory = CycleDetectingLockFactory.newInstance(CycleDetectingLockFactory.Policies.THROW);
        Lock lockA = factory.newReentrantLock(MyLockOrder.FIRST);
        Lock lockB = factory.newReentrantLock(MyLockOrder.SECOND);
        Lock lockC = factory.newReentrantLock(MyLockOrder.THIRD);
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("r1");
                lockA.lock();
                System.out.println("r1 get lockA");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    try {
                        lockB.lock();
                        System.out.println("r1 get lockB");
                    } finally {
                        lockB.unlock();
                    }
                } finally {
                    lockA.unlock();
                }
            }
        };
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                System.out.println("r2");
                lockB.lock();
                System.out.println("r2 get lockB");
                try {
                    try {
                        lockA.lock();
                        System.out.println("r2 get lockA");
                        try {
                            lockC.lock();
                        } finally {
                            lockC.unlock();;
                        }
                    } finally {
                        lockA.unlock();
                    }
                } finally {
                    lockB.unlock();
                }
            }
        };
        ExecutorService service = Executors.newCachedThreadPool();
//        service.submit(r1);
        service.submit(r2);
    }
}
