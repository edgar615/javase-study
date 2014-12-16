package concurreny.semaphore;

import java.util.concurrent.Semaphore;

/**
 * Created by Administrator on 2014/12/16.
 */
public class SemaphoreExample {

    public static void main(String[] args) throws InterruptedException {
        Semaphore available = new Semaphore(1, true);

        available.acquire();
        System.out.println("Acquire : " +available.availablePermits());

        available.release();
        System.out.println("Released : " +available.availablePermits());

        available.release();
        System.out.println("Released : " +available.availablePermits());

        available.release();
        System.out.println("Released : " +available.availablePermits());

        available.release();
        System.out.println("Released : " +available.availablePermits());

        available.acquire();
        System.out.println("Acquire : " +available.availablePermits());

        available.acquire();
        System.out.println("Acquire : " +available.availablePermits());

        available.acquire();
        System.out.println("Acquire : " +available.availablePermits());

        available.acquire();
        System.out.println("Acquire : " +available.availablePermits());

        available.acquire();
        System.out.println("Acquire : " +available.availablePermits());
    }
}
