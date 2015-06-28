package com.edgar.designpattern.command.door;

public class GarageDoor {
	
	String name;
	
	public GarageDoor(String name) {
		super();
		this.name = name;
	}

	public void up() {
		System.out.println("GarageDoor up");
	}
	
	public void down() {
		System.out.println("GarageDoor down");
	}
	
	public void stop() {
		System.out.println("GarageDoor down");
	}
}
