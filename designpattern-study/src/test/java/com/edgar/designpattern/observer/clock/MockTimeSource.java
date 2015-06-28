package com.edgar.designpattern.observer.clock;


public class MockTimeSource extends Subject implements TimeSource {
	private int hours;

	private int minutes;

	private int seconds;

	public void setTime(int hours, int minutes, int seconds) {
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
		notifyObservers();
	}

	@Override
	public int getHours() {
		return hours;
	}

	@Override
	public int getMinutes() {
		return minutes;
	}

	@Override
	public int getSeconds() {
		return seconds;
	}

}
