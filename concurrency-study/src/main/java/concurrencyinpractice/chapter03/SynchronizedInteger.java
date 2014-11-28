package concurrencyinpractice.chapter03;

import concurrencyinpractice.annotation.GuardedBy;
import concurrencyinpractice.annotation.ThreadSafe;

@ThreadSafe
public class SynchronizedInteger {

	@GuardedBy("this")
    private int value;

	public synchronized int getValue() {
		return value;
	}

	public synchronized void setValue(int value) {
		this.value = value;
	}

}