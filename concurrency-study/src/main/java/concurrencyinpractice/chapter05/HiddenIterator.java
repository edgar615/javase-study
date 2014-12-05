package concurrencyinpractice.chapter05;

import concurrencyinpractice.annotation.GuardedBy;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HiddenIterator {

	@GuardedBy("this")
    private final Set<Integer> set = new HashSet<Integer>();

	public synchronized void add(Integer i) {
		set.add(i);
	}

	public synchronized void remove(Integer i) {
		set.remove(i);
	}

	public void addTenThings() {
		Random r = new Random();
		for (int i = 0; i < 10; i++) {
			add(r.nextInt());
		}
		System.out.println("DEBUG: added ten elements to " + set);
	}
}