package com.edgar.designpattern.isp.door;

public interface Timer {

	void register(int timeout, TimerClient client);
}
