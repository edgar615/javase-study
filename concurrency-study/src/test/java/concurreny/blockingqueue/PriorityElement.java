package concurreny.blockingqueue;

import java.util.Comparator;

/**
 * Created by Administrator on 2014/11/25.
 */
public class PriorityElement implements Comparable<PriorityElement> {

    private final int priority;

    public PriorityElement(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "{priority : " + priority + "}";
    }

    @Override
    public int compareTo(PriorityElement o) {
        int diff = priority - o.priority;
        return ((diff == 0) ? 0 : ((diff < 0) ? -1 : 1));
    }
}
