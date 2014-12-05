package concurrencyinpractice.chapter04;

import concurrencyinpractice.annotation.GuardedBy;
import concurrencyinpractice.annotation.ThreadSafe;

@ThreadSafe
public class SafePoint {

	@GuardedBy("this")
    private int x;
	private int y;

	private SafePoint(int[] a) {
		 this(a[0], a[1]);
	}

	public SafePoint(SafePoint p) {
		this(p.get());
	}

	public SafePoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public synchronized int[] get() {
		return new int[] { x, y };
	}

	public synchronized void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
}