package com.edgar.designpattern.weather;

import java.util.Observer;

public class WeatherStation {

	private TemperatureSensor itsTemperature;
	private BarometricPressureSensor itsPressure;

	public WeatherStation(String toolkit) throws Exception {

		Class classFor = Class.forName(toolkit);
		StationToolkit st = (StationToolkit) classFor.newInstance();
		AlarmClock ac = new Alarm(st);

		TemperatureSensor ts = new TemperatureSensor(ac, st);
		BarometricPressureSensor bps = new BarometricPressureSensor(ac, st);
		BarometricPressureTrend bpt = new BarometricPressureTrend(bps);
	}

	public void addTempObserver(Observer o) {
		itsTemperature.addObserver(o);
	}

	public void addBPobserver(Observer o) {
		itsPressure.addObserver(o);
	}

}
