package com.edgar.hazelcast.primitives;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.IFunction;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/9/11.
 */
public class IAtomicLongExample {

    public static class MultiplyByTwoAndSubtractOne implements IFunction<Long, Long>, Serializable {

        @Override
        public Long apply(Long aLong) {
            return (long) (2 * aLong - 1);
        }
    }

    public static void main(String[] args) {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        final String NAME = "atomic";
        IAtomicLong atomicLong = instance.getAtomicLong(NAME);
        IAtomicLong atomicLong2 = instance.getAtomicLong(NAME);
        atomicLong.getAndSet(1);
        System.out.println("atomicLong2 is now: " + atomicLong2.getAndAdd(2));
        System.out.println("atomicLong is now: " + atomicLong.getAndAdd(0L));

        MultiplyByTwoAndSubtractOne alter = new MultiplyByTwoAndSubtractOne();
        atomicLong.alter(alter);
        System.out.println("atomicLong2 is now: " + atomicLong2.getAndAdd(0l));
        atomicLong2.alter(alter);
        System.out.println("atomicLong is now: " + atomicLong.getAndAdd(0L));

        System.exit(0);

    }
}
