package com.edgar.hazelcast.coll;

import com.hazelcast.core.QueueStore;

import java.util.*;

/**
 * Created by Administrator on 2015/9/11.
 */
public class QueueQStore implements QueueStore<String> {
    @Override
    public void store(Long l, String s) {
        System.out.println("storing " + s + " with " + l);
    }

    @Override
    public void storeAll(Map<Long, String> map) {
        System.out.println("store all");
    }

    @Override
    public void delete(Long l) {
        System.out.println("removing " + l);
    }

    @Override
    public void deleteAll(Collection<Long> collection) {
        System.out.println("deleteAll");
    }

    @Override
    public String load(Long l) {
        System.out.println("loading " + l);
        return "";
    }

    @Override
    public Map<Long, String> loadAll(Collection<Long> collection) {
        System.out.println("loadAll");
        Map<Long, String> retMap = new TreeMap<>();
        return retMap;
    }

    @Override
    public Set<Long> loadAllKeys() {
        System.out.println("loadAllKeys");
        return new TreeSet<>();
    }
}
