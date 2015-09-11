package com.edgar.hazelcast.coll;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ISet;

/**
 * Created by Administrator on 2015/9/11.
 *
 * ISet is a collection that does not keep order of the items placed in it. However, the elements are unique.
 * This collection implements the java.util.Set interface. Like ILists, this collection is not thread safe.
 * I suggest using the ILock agai
 */
public class HazelcastISet {
    public static void main(String[] args) {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        HazelcastInstance instance2 = Hazelcast.newHazelcastInstance();

        ISet<String> set = instance.getSet("set");
        set.add("Once");
        set.add("upon");
        set.add("a");
        set.add("time");

        ISet<String> set2 = instance2.getSet("set");
        for (String s : set2) {
            System.out.print(s);
        }
        System.exit(0);
    }
}
