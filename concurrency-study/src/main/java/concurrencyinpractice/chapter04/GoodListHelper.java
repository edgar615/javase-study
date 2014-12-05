package concurrencyinpractice.chapter04;

import concurrencyinpractice.annotation.ThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ThreadSafe
class GoodListHelper <E> {
    public List<E> list = Collections.synchronizedList(new ArrayList<E>());

    public boolean putIfAbsent(E x) {
        synchronized (list) {
            boolean absent = !list.contains(x);
            if (absent)
                list.add(x);
            return absent;
        }
    }
}