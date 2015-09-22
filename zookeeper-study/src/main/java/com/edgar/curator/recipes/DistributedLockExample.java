package com.edgar.curator.recipes;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryOneTime;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/9/21.
 */
public class DistributedLockExample {
    public static void main(String[] args) throws Exception {

        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.149.131:2181", new RetryOneTime(1000));
        client.start();

        InterProcessLock lock = new InterProcessMutex(client, "/lock");
        if (lock.acquire(5, TimeUnit.SECONDS)) {
            try {

            } finally {
                lock.release();
            }
        }
    }
}
