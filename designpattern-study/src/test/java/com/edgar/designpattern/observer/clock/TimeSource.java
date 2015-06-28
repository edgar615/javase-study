package com.edgar.designpattern.observer.clock;

public interface TimeSource {
	int getHours();

	int getMinutes();

	int getSeconds();
}
