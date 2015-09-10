package context;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用链表的形式表示ExecutionContext
 */
public class ExecutionContext {

    public static class Node {
        private final String contextId;
        private final String target;
        private final Object args;
        private final long startTime = System.currentTimeMillis();
        private long endTime = 0;
        private long totalTime = 0;
        private int level = 0;

        private Node parent   = null;
        private List<Node> children =
                new ArrayList<Node>();

        public Node(String contextId, String target, Object args) {
            this.contextId = contextId;
            this.target = target;
            this.args = args;
        }

        public void addChild(Node node) {
            children.add(node);
        }

        public String getContextId() {
            return contextId;
        }

        public String getTarget() {
            return target;
        }

        public Object getArgs() {
            return args;
        }

        public Node getParent() {
            return parent;
        }

        public List<Node> getChildren() {
            return children;
        }

        public long getStartTime() {
            return startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public long getTotalTime() {
            return totalTime;
        }

        public int getLevel() {
            return level;
        }
    }

    private static final ThreadLocal<Node> root = new ThreadLocal<Node>();

    private static final ThreadLocal<Node> currentParent = new ThreadLocal<Node>();

    public static void pre(String contextId, String locationId, Object args) {
        Node node = new Node(contextId, locationId, args);
        if (root.get() == null) {
            root.set(node);
        }
        node.parent = currentParent.get();
        if (node.parent != null) {
            node.level = node.parent.level + 1;
            node.parent.addChild(node);
        }
        currentParent.set(node);
    }

    public static void post() {
        Node node = currentParent.get();
        node.endTime = System.currentTimeMillis();
        node.totalTime = node.endTime - node.startTime;
        if (node.parent != null) {
            currentParent.set(node.parent);
//        } else {
//            root.remove();
//            currentParent.remove();
        }
    }

    public static Node root() {
        return root.get();
    }

    public static void clear() {
        root.remove();
        currentParent.remove();
    }
}
