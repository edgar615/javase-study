package com.edgar.zk;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/9/15.
 */
public class DataUpdater implements Watcher {
    private static String hostPort = "192.168.149.131:2181";
    private static String zooDataPath = "/MyConfig";
    ZooKeeper zk;

    public DataUpdater() {
        try {
            zk = new ZooKeeper(hostPort, 2000, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            String uuid = UUID.randomUUID().toString();
            byte zoo_data[] = uuid.getBytes();
            try {
                zk.setData(zooDataPath, zoo_data, -1);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.printf("\nEvent Received: %s", event.toString());
    }

    public static void main(String[] args) throws KeeperException, InterruptedException {
        DataUpdater updater = new DataUpdater();
        updater.run();
    }
}
