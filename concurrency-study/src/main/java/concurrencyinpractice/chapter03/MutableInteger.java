package concurrencyinpractice.chapter03;

import concurrencyinpractice.annotation.NotThreadSafe;

@NotThreadSafe
public class MutableInteger {

	private int value;

	public int getValue() {
        return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}