package com.edgar.hazelcast.primitives;

import com.hazelcast.core.*;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/9/11.
 */
public class IConditionExample {

    static final String LOCK_NAME = "lock";
    static final String CONDITION_NAME = "condition";
    static final String SERVER_NAME = "spinderrella";
    static final String LIST_NAME = "list";

    public static final void main(String[] args) {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();

        IExecutorService service = instance.getExecutorService(SERVER_NAME);
        service.execute(new SpunConsumer());
        service.execute(new SpunProducer());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println("Hey we got out sooner than I expected");
        } finally {
            instance.shutdown();
            System.exit(0);
        }
    }

    public static class SpunProducer implements Serializable, Runnable, HazelcastInstanceAware {

        private transient HazelcastInstance instance;

        private long counter = 0;

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
                if (list.isEmpty()) {
                    populate(list);
                    System.out.println("telling the consumers");
                    condition.signalAll();
                }
                for (int i = 0; i < 2; i++) {
                    while (!list.isEmpty()) {
                        System.out.println("Waiting for the list to be empty");
                        System.out.println("list size: " + list.size());
                        condition.await(2, TimeUnit.SECONDS);
                    }
                    populate(list);
                    System.out.println("Telling the consumers");
                    condition.signalAll();
                }
            } catch (InterruptedException e) {
                System.out.println("We have a found an interuption");
            } finally {
                condition.signalAll();
                System.out.println("Producer exiting stage left");
                lock.unlock();
            }
        }

        private void populate(IList list) {
            System.out.println("Populating list");
            long currentCounter = counter;
            for (; counter < currentCounter + 10; counter++) {
                list.add(counter);
            }
        }
    }

    public static class SpunConsumer implements Serializable, Runnable, HazelcastInstanceAware {

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
                for (int i = 0; i < 3; i++) {
                    while (list.isEmpty()) {
                        System.out.println("Waiting for the list to be filled");
                        condition.await(1, TimeUnit.SECONDS);
                    }
                    System.out.println("removing values");
                    while (!list.isEmpty()) {
                        System.out.println("value is " + list.get(0));
                        list.remove(0);
                    }
                    System.out.println("Signaling the producer");
                    condition.signalAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Consumer exiting stage right");
                condition.signalAll();
                lock.unlock();
            }
        }
    }
}
