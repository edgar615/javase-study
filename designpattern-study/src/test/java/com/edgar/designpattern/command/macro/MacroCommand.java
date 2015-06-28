package com.edgar.designpattern.command.macro;

import com.edgar.designpattern.command.Command;

public class MacroCommand implements Command {
	Command[] commands;

	public MacroCommand(Command[] commands) {
		super();
		this.commands = commands;
	}

	@Override
	public void execute() {
		for (int i = 0; i < commands.length; i++) {
			commands[i].execute();
		}
	}

	@Override
	public void undo() {
		for (int i = 0; i < commands.length; i++) {
			commands[i].undo();
		}

	}

}
