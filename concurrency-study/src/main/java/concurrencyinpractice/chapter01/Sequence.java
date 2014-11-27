package concurrencyinpractice.chapter01;

import concurrencyinpractice.annotation.GuardedBy;
import concurrencyinpractice.annotation.ThreadSafe;

@ThreadSafe
public class Sequence {

	@GuardedBy("this")
    private int value;

	public synchronized int getValue() {
		return value++;
	}
}