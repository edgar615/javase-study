package concurrent;

import com.google.common.util.concurrent.Striped;
import org.junit.Test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

/**
 * Created by edgar on 15-6-24.
 */
public class StripedTest {

    @Test
    public void testStriped() {
        Striped<Lock> lockStriped = Striped.lock(2);
        Lock lock1 = lockStriped.get(1);
        Lock lock2 = lockStriped.get(2);
        Lock lock3 = lockStriped.get(3);
        System.out.println(lock1 == lock3);
        System.out.println(lock1 == lock2);
        System.out.println(lock2 == lockStriped.get(4));
    }

    @Test
    public void testStripedLazy() {
        Striped<Lock> lockStriped = Striped.lazyWeakLock(2);
        Lock lock1 = lockStriped.get(1);
        Lock lock2 = lockStriped.get(2);
        Lock lock3 = lockStriped.get(3);
        System.out.println(lock1 == lock3);
        System.out.println(lock2 == lockStriped.get(4));
    }

    @Test
    public void testStriped2() {
        Striped<Semaphore> semaphoreStriped = Striped.semaphore(2, 2);
        System.out.println(semaphoreStriped.get(1).availablePermits());
        System.out.println(semaphoreStriped.get(2).availablePermits());

        System.out.println(semaphoreStriped.get(1) == semaphoreStriped.get(3));
        System.out.println(semaphoreStriped.get(1) == semaphoreStriped.get(2));
        System.out.println(semaphoreStriped.get(2) == semaphoreStriped.get(4));
    }
}
