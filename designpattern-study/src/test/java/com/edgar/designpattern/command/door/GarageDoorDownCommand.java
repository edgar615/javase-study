package com.edgar.designpattern.command.door;

import com.edgar.designpattern.command.Command;


public class GarageDoorDownCommand implements Command {

	GarageDoor garageDoor;

	public GarageDoorDownCommand(GarageDoor garageDoor) {
		this.garageDoor = garageDoor;
	}

	@Override
	public void execute() {
		garageDoor.down();
		System.out.println("Garage Door is down");
	}

	@Override
	public void undo() {
		garageDoor.up();
		System.out.println("Garage Door is up");
		
	}

}
