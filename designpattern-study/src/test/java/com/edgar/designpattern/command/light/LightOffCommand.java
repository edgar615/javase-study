package com.edgar.designpattern.command.light;

import com.edgar.designpattern.command.Command;

public class LightOffCommand implements Command {
	
	Light light;
	
	public LightOffCommand(Light light) {
		this.light = light;
	}

	@Override
	public void execute() {
		light.off();
	}

	@Override
	public void undo() {
		light.on();		
	}

}
