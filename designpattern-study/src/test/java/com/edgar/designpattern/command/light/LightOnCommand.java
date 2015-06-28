package com.edgar.designpattern.command.light;

import com.edgar.designpattern.command.Command;

public class LightOnCommand implements Command {
	
	Light light;
	
	public LightOnCommand(Light light) {
		this.light = light;
	}

	@Override
	public void execute() {
		light.on();
	}

	@Override
	public void undo() {
		light.off();
	}

}
