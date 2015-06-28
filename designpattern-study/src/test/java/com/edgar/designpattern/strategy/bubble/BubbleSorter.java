package com.edgar.designpattern.strategy.bubble;

public class BubbleSorter {
	private int operations = 0;
	protected int length = 0;
	private SortHandle sortHandle = null;

	private BubbleSorter(SortHandle sortHandle) {
		super();
		this.sortHandle = sortHandle;
	}

	public int sort(Object array) {
		sortHandle.setArray(array);
		length = sortHandle.length();
		operations = 0;
		if (length <= 1) {
			return operations;
		}

		for (int nextToLast = length - 2; nextToLast >= 0; nextToLast--) {
			for (int index = 0; index <= nextToLast; index++) {
				if (sortHandle.outOfOrder(index)) {
					sortHandle.swap(index);
				}
				operations++;
			}
		}
		return operations;
	}
}
