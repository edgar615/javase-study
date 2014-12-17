package concurrencyinpractice.chapter15;

/**
 * Created by Administrator on 2014/12/17.
 */
public class CasCounter {

    private SimulatedCAS value;

    public int getValue() {
        return value.get();
    }

    public int increment() {
        int v;
        do {
            v = value.get();
        } while (v != value.compareAndSwap(v, v + 1));
        return v + 1;

    }
}
