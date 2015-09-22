package com.edgar.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.CreateMode;

/**
 * Created by Administrator on 2015/9/15.
 */
public class MyCuratorClient {

    public static void main(String[] args) throws Exception {
        //Getting a Connection
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.149.131:2181", new RetryOneTime(1000));
        client.start();


        client.delete().forPath("/my/path");
        //Calling ZooKeeper Directly
        client.create().forPath("/my/path", "Hello, curator!".getBytes());

        client.delete().inBackground().forPath("/head");
        client.create().forPath("/head", new byte[0]);
        client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/head/child", new byte[0]);
        client.getData().watched().inBackground().forPath("/test");
    }
}
