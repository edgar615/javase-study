package com.edgar.hazelcast.primitives;

import com.hazelcast.core.*;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/9/11.
 */
public class ILockExample {
    static final String LIST_NAME = "to be locked";
    static final String LOCK_NAME = "to lock with";
    static final String CONDITION_NAME = "to singal with";

    public static void main(String[] args) {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        IExecutorService service = instance.getExecutorService("service");
        ListConsumer consumer = new ListConsumer();
        ListProducer producer = new ListProducer();

        try {
            service.submit(producer);
            service.submit(consumer);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Got interrupted");
        } finally {
            instance.shutdown();
        }
    }

    public static class ListConsumer implements Runnable, Serializable, HazelcastInstanceAware {

        private transient HazelcastInstance instance;

        @Override
        public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
            this.instance = hazelcastInstance;
        }

        @Override
        public void run() {
            ILock lock = instance.getLock(LOCK_NAME);
            ICondition condition = lock.newCondition(CONDITION_NAME);
            IList list = instance.getList(LIST_NAME);
            lock.lock();
            try {
                while (list.isEmpty()) {
                    condition.await(2, TimeUnit.SECONDS);
                }
                while (!list.isEmpty()) {
                    System.out.println("value is " + list.get(0));
                    list.remove(0);
                }
            } catch (InterruptedException e) {
                System.out.println("Consumer got interrupted");
            } finally {
                lock.unlock();
            }
            System.out.println("Consumer leaving");
        }
    }

    public static class ListProducer implements Runnable, Serializable, HazelcastInstanceAware {
        private  transient  HazelcastInstance instance;

        @Override
        public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
            this.instance = hazelcastInstance;
        }

        @Override
        public void run() {
            ILock lock = instance.getLock(LOCK_NAME);
            ICondition condition = lock.newCondition(CONDITION_NAME);
            IList list = instance.getList(LIST_NAME);
            lock.lock();
            try {
                for (int i = 1; i <= 10; i ++) {
                    list.add(i);
                }
                condition.signalAll();
            } finally {
                lock.unlock();
            }
            System.out.println("Producer leaving");
        }
    }
}
