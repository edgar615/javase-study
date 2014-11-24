package thread.readwrite;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2014/11/24.
 * 写锁可以降级为读锁
 */
public class ReadWriteLock4 {
    private int readers = 0;
    private int writeAccesses    = 0;
    private int writeRequests = 0;

    private Map<Thread, Integer> readingThreads = new HashMap<Thread, Integer>();
    private Thread writingThread = null;

    public synchronized void lockRead()
            throws InterruptedException{
        Thread callingThread = Thread.currentThread();
        while(! canGrantReadAccess(callingThread)){
            wait();
        }
        readers ++;
        readingThreads.put(callingThread, getReadAccessCount(callingThread));
    }

    public synchronized void unLockRead() {
        Thread callingThread = Thread.currentThread();
        int accessCount = getReadAccessCount(callingThread);
        if(accessCount == 1) {
            readingThreads.remove(callingThread);
        } else {
            readingThreads.put(callingThread, (accessCount -1));
        }
        notifyAll();
    }

    public synchronized void lockWrite() throws InterruptedException {
        writeRequests ++;
        Thread callingThread = Thread.currentThread();
        while (!canGrantWriteAccess(callingThread)) {
            wait();
        }
        writeAccesses ++;
        writeRequests --;
        writingThread = callingThread;
    }

    public synchronized void unLockWrite() {
        writeAccesses --;
        if (writeAccesses == 0) {
            writingThread = null;
        }
        notifyAll();
    }

    private boolean canGrantWriteAccess(Thread callingThread){
        if (isOnlyReader(callingThread)) return  true;
        if(hasReaders()) return false;
        if(writingThread == null)    return true;
        if(!isWriter(callingThread)) return false;
        return true;
    }

    private boolean hasReaders(){
        return readingThreads.size() > 0;
    }

    private boolean isWriter(Thread callingThread){
        return writingThread == callingThread;
    }

    private boolean isOnlyReader(Thread thread) {
        return readers == 0 && readingThreads.get(thread) != null;
    }

    private boolean canGrantReadAccess(Thread callingThread){
        if(isWriter(callingThread)) return true;
        if(writeAccesses > 0) return false;
        if(isReader(callingThread)) return true;
        if(writeRequests > 0) return false;
        return true;
    }

    private boolean isReader(Thread callingThread){
        return readingThreads.get(callingThread) != null;
    }

    private int getReadAccessCount(Thread callingThread){
        Integer accessCount = readingThreads.get(callingThread);
        if(accessCount == null) return 0;
        return accessCount.intValue();
    }
}
