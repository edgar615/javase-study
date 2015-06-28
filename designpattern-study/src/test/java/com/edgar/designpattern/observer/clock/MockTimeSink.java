package com.edgar.designpattern.observer.clock;

public class MockTimeSink implements Observer {

	private int hours;

	private int minutes;

	private int seconds;

	private TimeSource source;

	public MockTimeSink(TimeSource source) {
		super();
		this.source = source;
	}

	@Override
	public void update() {
		this.hours = source.getHours();
		this.minutes = source.getMinutes();
		this.seconds = source.getSeconds();
	}

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public TimeSource getSource() {
        return source;
    }

    public void setSource(TimeSource source) {
        this.source = source;
    }
}
