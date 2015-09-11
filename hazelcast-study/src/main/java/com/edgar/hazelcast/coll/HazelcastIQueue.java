package com.edgar.hazelcast.coll;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import com.hazelcast.core.IQueue;

/**
 * Created by Administrator on 2015/9/11.
 *
 * IQueue is a collection that keeps the order of what comes in and allows duplicates.
 * It implements the java.util.concurrent.BlockingQueue so it is thread safe.
 * This is the most scalable of the collections because its capacity grows as the number of instances go up.
 * For instance, lets say there is a limit of 10 items for a queue.
 * Once the queue is full, no more can go in there unless another Hazelcast instance comes up, then another 10 spaces are available.
 * A copy of the queue is also made.
 * IQueues can also be persisted via implementing the interface QueueStore.
 */
public class HazelcastIQueue {
    public static void main(String[] args) {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        HazelcastInstance instance2 = Hazelcast.newHazelcastInstance();

        IQueue<String> queue = instance.getQueue("queue");
        queue.add("Once");
        queue.add("upon");
        queue.add("a");
        queue.add("time");

        IQueue<String> queue2 = instance.getQueue("queue");
        for (String s : queue2) {
            System.out.print(s);
        }
        System.exit(0);
    }
}
