package thread.deadlock;

import java.util.ArrayList;
import java.util.List;

/**
 * 如果线程1调用parent.addChild(child)方法的同时有另外一个线程2调用child.setParent(parent)方法，两个线程中的parent表示的是同一个对象，child亦然，此时就会发生死锁。
 */
public class TreeNode {
    TreeNode parent = null;
    List children = new ArrayList();

    public synchronized void addChild(TreeNode child) {
        if (!this.children.contains(child)) {
            this.children.add(child);
            child.setParentOnly(this);
        }
    }

    public synchronized void addChildOnly(TreeNode child) {
        if (!this.children.contains(child)) {
            this.children.add(child);
        }
    }

    public synchronized void setParent(TreeNode parent) {
        this.parent = parent;
        parent.addChildOnly(this);
    }

    public synchronized void setParentOnly(TreeNode parent) {
        this.parent = parent;
    }
}