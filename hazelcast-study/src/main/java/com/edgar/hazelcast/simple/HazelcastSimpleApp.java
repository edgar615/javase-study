package com.edgar.hazelcast.simple;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IdGenerator;

import java.util.Map;

/**
 * Created by edgar on 15-9-10.
 * http://www.javacodegeeks.com/2013/11/getting-started-with-hazelcast.html
 * http://www.javacodegeeks.com/2014/10/beginners-guide-to-hazelcast-part-1-2.html
 */
public class HazelcastSimpleApp {
    public static void main(String[] args) {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        HazelcastInstance instance2 = Hazelcast.newHazelcastInstance();

        //Notice that I didn’t even use the IMap interface when I retrieved an instance of the map.  I just used the java.util.Map interface.
        // This isn’t good for using the distributed features of Hazelcast but for this example, it works fine.
        Map<Object, Object> map = instance.getMap("a");
        IdGenerator gen = instance.getIdGenerator("gen");
        for (int i = 0; i < 10; i ++) {
            map.put(gen.newId(), "stuff " + i);
        }

        Map<Object, Object> map2 = instance2.getMap("a");
        for (Map.Entry entry : map2.entrySet()) {
            System.out.printf("entry: %d; %s\n", entry.getKey(), entry.getValue());
        }
        System.exit(0);
    }
}
