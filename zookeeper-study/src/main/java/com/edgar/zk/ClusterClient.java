package com.edgar.zk;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.lang.management.ManagementFactory;

/**
 * Created by Administrator on 2015/9/15.
 */
public class ClusterClient implements Watcher, Runnable {

    private static String membershipRoot = "/Members";

    ZooKeeper zk;

    public ClusterClient(String hostPort, Long pid) {
        String proccessId = pid.toString();
        try {
            zk = new ZooKeeper(hostPort, 2000, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (zk != null) {
            try {
                zk.create(membershipRoot + "/" + proccessId, proccessId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void close() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                while (true) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            this.close();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.printf("\nEvent Received: %s", event.toString());
    }

    public static void main(String[] args) {
//Get the process id
        String name = ManagementFactory.getRuntimeMXBean().getName();
        int index = name.indexOf('@');
        Long processId = Long.parseLong(name.substring(0, index));
        new ClusterClient("192.168.149.131:2181", processId).run();
    }
}
