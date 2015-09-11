package com.edgar.hazelcast.primitives;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.core.ICountDownLatch;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.core.IList;
import com.hazelcast.core.ILock;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Daryl
 */
public class ICountDownLatchExample {
    static final String LOCK_NAME = "lock";
    static final String LATCH_NAME = "condition";
    static final String SERVICE_NAME = "spinderella";
    static final String LIST_NAME = "list";
    
    public static final void main(String[] args) {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        
        IExecutorService service  = instance.getExecutorService(SERVICE_NAME);
        service.execute(new SpunMaster());
        service.execute(new SpunSlave());
        
        
        
        try {
            Thread.sleep(10000);

        } catch(InterruptedException ie) {
            System.out.println("Hey we got out sooner than I expected");
        } finally {
            instance.shutdown();
            System.exit(0);
        }
    }
    
    public static class SpunMaster implements Serializable, Runnable, HazelcastInstanceAware {

        private transient HazelcastInstance instance;
        private long counter = 0;
        
        @Override
        public void run() {
            ILock lock = instance.getLock(LOCK_NAME);
            ICountDownLatch latch = instance.getCountDownLatch(LATCH_NAME);
            IList list = instance.getList(LIST_NAME);
            
            lock.lock();            
            try {
                latch.trySetCount(10);
                populate(list, latch);
            } finally {
                System.out.println("Master exiting stage left");
                lock.unlock();
            }
        }

        @Override
        public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
            instance = hazelcastInstance;
        }
        
        private void populate(IList list, ICountDownLatch latch) {
            System.out.println("Populating list");
            long currentCounter = counter;
            for(; counter < currentCounter + 10; counter++) {
                list.add(counter);
                latch.countDown();
            }
        }
    }
    
    public static class SpunSlave implements Serializable, Runnable, HazelcastInstanceAware {

        private transient HazelcastInstance instance;
        
        @Override
        public void run() {
            ILock lock = instance.getLock(LOCK_NAME);
            ICountDownLatch latch = instance.getCountDownLatch(LATCH_NAME);
            IList list = instance.getList(LIST_NAME);
            
            lock.lock();            
            try {
                if(latch.await(2, TimeUnit.SECONDS)) {
                    while(!list.isEmpty()){
                        System.out.println("value is " + list.get(0));
                        list.remove(0);
                    }

                }
            } catch(InterruptedException ie) {
                System.out.println("We had an interrupt");
            } finally {
                System.out.println("Slave exiting stage right");
                lock.unlock();
            }
        }

        @Override
        public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
            instance = hazelcastInstance;
        }
    }

}