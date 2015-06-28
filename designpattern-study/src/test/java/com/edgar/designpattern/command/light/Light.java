package com.edgar.designpattern.command.light;

public class Light {
	
	String name;

	public Light(String name) {
		super();
		this.name = name;
	}

	public void on() {
		System.out.println("Light on");
	}
	
	public void off() {
		System.out.println("Light off");
	}
}
