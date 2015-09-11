package com.edgar.hazelcast.coll;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import com.hazelcast.core.ISet;

/**
 * Created by Administrator on 2015/9/11.
 *
 * IList is a collection that keeps the order of what is put in and can have duplicates.
 * In fact, it implements the java.util.List interface.
 * This is not thread safe and one must use some sort of mutex or lock to control access by many threads.
 * I suggest Hazelcast’s ILock.
 */
public class HazelcastIList {
    public static void main(String[] args) {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        HazelcastInstance instance2 = Hazelcast.newHazelcastInstance();

        IList<String> list = instance.getList("list");
        list.add("Once");
        list.add("upon");
        list.add("a");
        list.add("time");

        IList<String> list2 = instance.getList("list");
        for (String s : list2) {
            System.out.print(s);
        }
        System.exit(0);
    }
}
