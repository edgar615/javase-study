package concurrencyinpractice.chapter04;

import concurrencyinpractice.annotation.ThreadSafe;

import java.util.Vector;

@ThreadSafe
public class BetterVector<E> extends Vector<E> {

	public synchronized boolean putIfAbsent(E x) {
        boolean absent = !contains(x);
		if (absent) {
			add(x);
		}
		return absent;
	}
}