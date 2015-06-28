package com.edgar.designpattern.strategy.bubble;

public class QuickBubbleSorter {
	private int operations = 0;
	protected int length = 0;
	private SortHandle sortHandle = null;

	private QuickBubbleSorter(SortHandle sortHandle) {
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

		boolean thisPassInOrder = false;
		for (int nextToLast = length - 2; nextToLast >= 0; nextToLast--) {
			thisPassInOrder = true;
			for (int index = 0; index <= nextToLast; index++) {
				if (sortHandle.outOfOrder(index)) {
					sortHandle.swap(index);
					thisPassInOrder = false;
				}
				operations++;
			}
		}
		return operations;
	}
}
