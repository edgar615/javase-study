Semaphore 通常用于限制可以访问某些资源（物理或逻辑的）的线程数目

当一个线程想要访问某个共享资源，首先，它必须获得semaphore。如果semaphore的内部计数器的值大于0，那么semaphore减少计数器的值并允许访问共享的资源。计数器的值大于0表示，有可以自由使用的资源，所以线程可以访问并使用它们。

另一种情况，如果semaphore的计数器的值等于0，那么semaphore让线程进入休眠状态一直到计数器大于0。计数器的值等于0表示全部的共享资源都正被线程们使用，所以此线程想要访问就必须等到某个资源成为自由的。

<pre>
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
</pre>

输出 ：
    Acquire : 0
    Released : 1
    Released : 2
    Released : 3
    Released : 4
    Acquire : 3
    Acquire : 2
    Acquire : 1
    Acquire : 0

<pre>
public class PrintQueue {
    private final Semaphore semaphore = new Semaphore(1);

    public void printJob (Object document){
        try {
            semaphore.acquire();
            long duration=(long)(Math.random()*10);
            System.out.printf("%s: PrintQueue: Printing a Job during %d seconds\n",Thread.currentThread().getName(),duration);
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}
</pre>

<pre>
public class Job implements Runnable {
    private PrintQueue printQueue;

    public Job(PrintQueue printQueue) {
        this.printQueue = printQueue;
    }

    @Override
    public void run() {
        System.out.printf("%s: Going to print a job\n",Thread. currentThread().getName());
        printQueue.printJob(new Object());
        System.out.printf("%s: The document has been printed\n",Thread.currentThread().getName());
    }
}
</pre>

<pre>
    public static void main(String[] args) {
        PrintQueue printQueue=new PrintQueue();
        Thread thread[]=new Thread[10];
        for (int i=0; i<10; i++){
            thread[i]=new Thread(new Job(printQueue),"Thread"+i);
        }
        for (int i=0; i<10; i++){
            thread[i].start();
        }
    }
</pre>

-  	acquire() 从此信号量获取一个许可，在提供一个许可前一直将线程阻塞，否则线程被中断。在阻塞期间，线程可能会被中断，然后此方法抛出InterruptedException异常
- 	acquire(int permits) 从此信号量获取给定数目的许可，在提供这些许可前一直将线程阻塞，或者线程已被中断。在阻塞期间，线程可能会被中断，然后此方法抛出InterruptedException异常
-   acquireUninterruptibly() 从此信号量中获取许可，在有可用的许可前将其阻塞。
-   acquireUninterruptibly(int permits) 从此信号量获取给定数目的许可，在提供这些许可前一直将线程阻塞。
-   tryAcquire() 仅在调用时此信号量存在一个可用许可，才从信号量获取许可。
-   tryAcquire(long timeout, TimeUnit unit) 如果在给定的等待时间内，此信号量有可用的许可并且当前线程未被中断，则从此信号量获取一个许可。
-   tryAcquire(int permits) 仅在调用时此信号量中有给定数目的许可时，才从此信号量中获取这些许可。
-   tryAcquire(int permits, long timeout, TimeUnit unit) 如果在给定的等待时间内此信号量有可用的所有许可，并且当前线程未被中断，则从此信号量获取给定数目的许可

**tryAcquire的具体用法详细阅读API**

**Semaphores的公平性**

- Semaphore(int permits, boolean fair)  创建具有给定的许可数和给定的公平设置的 Semaphore。 

此类的构造方法可选地接受一个公平 参数。当设置为 false 时，此类不对线程获取许可的顺序做任何保证。特别地，闯入 是允许的，也就是说可以在已经等待的线程前为调用 acquire() 的线程分配一个许可，从逻辑上说，就是新线程将自己置于等待线程队列的头部。当公平设置为 true 时，信号量保证对于任何调用获取方法的线程而言，都按照处理它们调用这些方法的顺序（即先进先出；FIFO）来选择线程、获得许可。注意，FIFO 排序必然应用到这些方法内的指定内部执行点。所以，可能某个线程先于另一个线程调用了 acquire，但是却在该线程之后到达排序点，并且从方法返回时也类似。还要注意，非同步的 tryAcquire 方法不使用公平设置，而是使用任意可用的许可。 