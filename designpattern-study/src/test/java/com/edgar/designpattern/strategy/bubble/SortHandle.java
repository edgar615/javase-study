package com.edgar.designpattern.strategy.bubble;

public interface SortHandle {

	void swap(int index);
	boolean outOfOrder(int index);
	int length();
	void setArray(Object array);
}
