package com.edgar.designpattern.observer.clock;

import java.util.ArrayList;
import java.util.List;

public class Subject {

	private List<Observer> observers = new ArrayList<Observer>();

	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update();
		}
	}

	public void registerObserver(Observer observer) {
		observers.add(observer);
	}
}
