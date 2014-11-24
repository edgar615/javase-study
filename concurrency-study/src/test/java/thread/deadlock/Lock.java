package thread.deadlock;

import thread.busywait.MonitorObject;

//lock implementation with nested monitor lockout problem

/**
 * lock()在调用monitorObject.wait()方法时会释放monitorObject的锁，等待唤醒，但不会释放this的锁
 * unlock()需要获取this的锁才能调用monitorObject.notify()，因此发生死锁。
 */
public class Lock{
    protected MonitorObject monitorObject = new MonitorObject();
	protected boolean isLocked = false;

	public void lock() throws InterruptedException{
		synchronized(this){
			while(isLocked){
				synchronized(this.monitorObject){
					this.monitorObject.wait();
				}
			}
			isLocked = true;
		}
	}

	public void unlock(){
		synchronized(this){
			this.isLocked = false;
			synchronized(this.monitorObject){
				this.monitorObject.notify();
			}
		}
	}
}