package com.edgar.designpattern.command.door;

import com.edgar.designpattern.command.Command;


public class GarageDoorUpCommand implements Command {

	GarageDoor garageDoor;

	public GarageDoorUpCommand(GarageDoor garageDoor) {
		this.garageDoor = garageDoor;
	}

	@Override
	public void execute() {
		garageDoor.up();
		System.out.println("Garage Door is Open");
	}

	@Override
	public void undo() {
		garageDoor.down();
		System.out.println("Garage Door is down");		
	}

}
