package com.edgar.curator.recipes;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.RetryOneTime;

/**
 * Created by Administrator on 2015/9/21.
 */
public class LeaderElectionExample {

    public static void main(String[] args) {
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.149.131:2181", new RetryOneTime(1000));

        LeaderSelectorListener listener = new LeaderSelectorListener() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                // this callback will get called when you are the leader
                // do whatever leader work you need to and only exit
                // this method when you want to relinquish leadership
            }

            @Override
            public void stateChanged(CuratorFramework client, ConnectionState newState) {
                // see https://github.com/Netflix/curator/wiki/Errors
                // You always need to be aware that connections to ZK can fail
            }
        };
        LeaderSelector selector = new LeaderSelector(client, "/leader", listener);
        selector.autoRequeue();
        selector.start();
    }
}
