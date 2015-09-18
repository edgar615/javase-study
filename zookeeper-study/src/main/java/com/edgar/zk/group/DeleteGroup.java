package com.edgar.zk.group;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2015/9/18.
 */
public class DeleteGroup extends ConnectionWatcher {

    public void delete(String groupName) {
        String path = "/" + groupName;
        try {
            List<String> children = zk.getChildren(path, false);
            for (String child : children) {
                zk.delete(path + "/" + child, -1);
            }
            zk.delete(path, -1);
        } catch (KeeperException.NoNodeException e) {
            System.out.printf("Group %s does not exists\n", groupName);
            System.exit(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        DeleteGroup group = new DeleteGroup();
        group.connect("192.168.149.131:2181");
        group.delete("hello");
        group.close();
    }
}
