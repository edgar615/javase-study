package concurrent;

import com.google.common.util.concurrent.CycleDetectingLockFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2015/6/23.
 */
public class CycleDetectingLockExample {
    public static void main(String[] args) {
        //死锁
//        Lock lockA = new ReentrantLock();
//        Lock lockB = new ReentrantLock();
        CycleDetectingLockFactory.WithExplicitOrdering<MyLockOrder> factory =
           CycleDetectingLockFactory.newInstanceWithExplicitOrdering(MyLockOrder.class, CycleDetectingLockFactory.Policies.THROW);
//        CycleDetectingLockFactory factory = CycleDetectingLockFactory.newInstance(CycleDetectingLockFactory.Policies.THROW);
        Lock lockA = factory.newReentrantLock(MyLockOrder.FIRST);
        Lock lockB = factory.newReentrantLock(MyLockOrder.SECOND);
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
                    lockA.lock();
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
                    } finally {
                        lockA.unlock();
                    }
                } finally {
                    lockB.lock();
                }
            }
        };
        ExecutorService service = Executors.newCachedThreadPool();
//        service.submit(r1);
        service.submit(r2);
    }
}
