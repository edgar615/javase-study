package concurreny.blockingqueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2014/11/25.
 */
public class DelayedElement<T> implements Delayed {

    private final long origin;

    private final long delay;

    private final T element;

    public DelayedElement(T element, long delay) {
        this.element = element;
        this.delay = delay;
        this.origin = System.currentTimeMillis();
    }

    /**
     * 还有多久过期
     *
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(delay - (System.currentTimeMillis() - origin), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (o == this) {
            return 0;
        }
        if (o instanceof DelayedElement) {
            long diff = origin + delay - ((DelayedElement) o).delay - ((DelayedElement) o).origin;
            return ((diff == 0) ? 0 : ((diff < 0) ? -1 : 1));
        }
        long d = (getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        return ((d == 0) ? 0 : ((d < 0) ? -1 : 1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DelayedElement that = (DelayedElement) o;

        if (element != null ? !element.equals(that.element) : that.element != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return element != null ? element.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "{" +
                "data='" + element + '\'' +
                ", startTime=" + origin +
                ", delay=" + delay +
                '}';
    }
}
