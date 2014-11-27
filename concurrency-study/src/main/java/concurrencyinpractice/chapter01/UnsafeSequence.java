package concurrencyinpractice.chapter01;

import concurrencyinpractice.annotation.NotThreadSafe;

@NotThreadSafe
public class UnsafeSequence {

	private int value;

	public int getValue() {
        return value++;
	}
}