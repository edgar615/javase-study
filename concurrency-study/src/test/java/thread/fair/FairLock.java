package thread.fair;

import thread.fair.QueueObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 每一个调用lock()的线程都会进入一个队列，当解锁后，只有队列里的第一个线程被允许锁住Farlock实例，所有其它的线程都将处于等待状态，直到他们处于队列头部。
 */
public class FairLock {
    private boolean           isLocked       = false;
    private Thread            lockingThread  = null;
    private List<QueueObject> waitingThreads =
            new ArrayList<QueueObject>();

    public void lock() throws InterruptedException{
        QueueObject queueObject           = new QueueObject();
        boolean     isLockedForThisThread = true;
        synchronized(this){
            waitingThreads.add(queueObject);
        }

        while(isLockedForThisThread){
            synchronized(this){
                isLockedForThisThread =
                        isLocked || waitingThreads.get(0) != queueObject;
                if(!isLockedForThisThread){
                    isLocked = true;
                    waitingThreads.remove(queueObject);
                    lockingThread = Thread.currentThread();
                    return;
                }
            }
            try{
                queueObject.doWait();
            }catch(InterruptedException e){
                synchronized(this) { waitingThreads.remove(queueObject); }
                throw e;
            }
        }
    }

    public synchronized void unlock(){
        if(this.lockingThread != Thread.currentThread()){
            throw new IllegalMonitorStateException(
                    "Calling thread has not locked this lock");
        }
        isLocked      = false;
        lockingThread = null;
        if(waitingThreads.size() > 0){
            waitingThreads.get(0).doNotify();
        }
    }
}
