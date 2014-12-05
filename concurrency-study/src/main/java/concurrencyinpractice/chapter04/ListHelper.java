package concurrencyinpractice.chapter04;

import concurrencyinpractice.annotation.NotThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NotThreadSafe
public class ListHelper<E> {

	public List<E> list = Collections.synchronizedList(new ArrayList<E>());

	public synchronized boolean putIfAbsent(E x) {
        boolean absent = !list.contains(x);
		if (absent) {
			list.add(x);
		}
		return absent;
	}

}