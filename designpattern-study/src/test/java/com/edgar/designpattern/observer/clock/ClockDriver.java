package com.edgar.designpattern.observer.clock;//package com.edgar.designpattern.observer.clock;
//
//public class ClockDriver implements ClockObserver {
//
//	private TimeSink sink;
//
//	public ClockDriver(Subject source, TimeSink sink) {
//		source.registerObserver(this);
//		this.sink = sink;
//	}
//
//	@Override
//	public void update(int hours, int minutes, int seconds) {
//		this.sink.setTime(hours, minutes, seconds);
//	}
//
//}
