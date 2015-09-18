package com.edgar.zk.group;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2015/9/18.
 */
public class ListGroup extends ConnectionWatcher {

    public void list(String groupname) throws KeeperException, InterruptedException {
        String path = "/" + groupname;

        try {
            //如果在一个znode上设置了观察标志，那么一旦该znode的状态改变，关联的观察会被处罚
            List<String> children = zk.getChildren(path, false);
            if (children.isEmpty()) {
                System.out.printf("No members in grup %s\n", groupname);
                System.exit(1);
            }
            for (String child : children) {
                System.out.println(child);
            }
        } catch (KeeperException.NoNodeException e) {
            System.out.printf("Group %s does not exists\n", groupname);
            System.exit(1);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ListGroup group = new ListGroup();
        group.connect("192.168.149.131:2181");
        group.list("");
        group.close();
    }
}
