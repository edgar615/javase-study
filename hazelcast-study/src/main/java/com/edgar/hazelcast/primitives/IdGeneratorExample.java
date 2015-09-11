package com.edgar.hazelcast.primitives;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IdGenerator;

/**
 * Created by Administrator on 2015/9/11.
 */
public class IdGeneratorExample {

    public static void main(String[] args) {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        IdGenerator generator = instance.getIdGenerator("gen");

        for (int i = 0; i < 10; i ++) {
            System.out.println("The generated value is " + generator.newId());
        }
        instance.shutdown();
        System.exit(0);
    }
}
