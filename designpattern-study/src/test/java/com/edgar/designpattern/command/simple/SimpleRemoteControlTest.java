package com.edgar.designpattern.command.simple;

public class SimpleRemoteControlTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SimpleRemoteControl remote = new SimpleRemoteControl();
		Light light = new Light();
		LightOnCommand lightOn = new LightOnCommand(light);

		remote.setCommand(lightOn);
		remote.buttonWasPressed();

		GarageDoorOpenCommand garageOpen = new GarageDoorOpenCommand(
				new GarageDoor());
		remote.setCommand(garageOpen);
		remote.buttonWasPressed();
	}
}
