package com.edgar.designpattern.command.simple;

public class GarageDoorOpenCommand implements Command {

	GarageDoor garageDoor;

	public GarageDoorOpenCommand(GarageDoor garageDoor) {
		this.garageDoor = garageDoor;
	}

	@Override
	public void execute() {
		garageDoor.up();
		System.out.println("Garage Door is Open");
	}

}
