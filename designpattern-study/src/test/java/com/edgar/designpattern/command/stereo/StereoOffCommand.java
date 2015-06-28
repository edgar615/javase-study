package com.edgar.designpattern.command.stereo;

import com.edgar.designpattern.command.Command;

public class StereoOffCommand implements Command {

	Stereo stereo;
	
	public StereoOffCommand(Stereo stereo) {
		this.stereo = stereo;
	}
	
	@Override
	public void execute() {
		stereo.off();
	}

	@Override
	public void undo() {
		stereo.on();
	}

}
