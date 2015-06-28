package com.edgar.designpattern.weather;

import java.util.Observable;

public class TemperatureSensor extends Observable {

	private TemperatureSensorImpl sensorImpl;

	public TemperatureSensor(AlarmClock ac, StationToolkit st) {
		sensorImpl = st.makeTemperature();
	}

}
