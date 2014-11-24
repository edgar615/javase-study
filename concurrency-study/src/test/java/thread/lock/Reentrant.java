package thread.lock;

/**
 * 如果一个线程已经拥有了一个管程对象上的锁，那么它就有权访问被这个管程对象同步的所有代码块。这就是可重入。
 * 线程可以进入任何一个它已经拥有的锁所同步着的代码块。
 */
public class Reentrant{
    public synchronized void outer(){
		inner();
	}

	public synchronized void inner(){
		//do something
	}
}