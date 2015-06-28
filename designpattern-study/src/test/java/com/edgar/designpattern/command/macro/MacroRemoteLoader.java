package com.edgar.designpattern.command.macro;

import com.edgar.designpattern.command.Command;
import com.edgar.designpattern.command.RemoteControl;
import com.edgar.designpattern.command.door.GarageDoor;
import com.edgar.designpattern.command.door.GarageDoorDownCommand;
import com.edgar.designpattern.command.door.GarageDoorUpCommand;
import com.edgar.designpattern.command.light.Light;
import com.edgar.designpattern.command.light.LightOffCommand;
import com.edgar.designpattern.command.light.LightOnCommand;
import com.edgar.designpattern.command.stereo.Stereo;
import com.edgar.designpattern.command.stereo.StereoOffCommand;
import com.edgar.designpattern.command.stereo.StereoOnWithCDCommand;

public class MacroRemoteLoader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RemoteControl remoteControl = new RemoteControl();

		Light livingRoomLight = new Light("Living Room");
		Light kitchenLight = new Light("Kitchen");
		GarageDoor garageDoor = new GarageDoor("");
		Stereo stereo = new Stereo("Living Room");

		LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
		LightOffCommand livingRoomLightOff = new LightOffCommand(
				livingRoomLight);
		LightOnCommand kitchenLightOn = new LightOnCommand(kitchenLight);
		LightOffCommand kitchenLightOff = new LightOffCommand(kitchenLight);

		GarageDoorUpCommand garageDoorUp = new GarageDoorUpCommand(garageDoor);
		GarageDoorDownCommand garageDoorDown = new GarageDoorDownCommand(
				garageDoor);

		StereoOnWithCDCommand stereoOnWithCD = new StereoOnWithCDCommand(stereo);
		StereoOffCommand stereoOff = new StereoOffCommand(stereo);

		
		Command[] partyOn = {livingRoomLightOn,kitchenLightOn,garageDoorUp,stereoOnWithCD};
		Command[] partyOff = {livingRoomLightOff,kitchenLightOff,garageDoorDown,stereoOff};
		
		MacroCommand partyOnCommand = new MacroCommand(partyOn);
		MacroCommand partyOffCommand = new MacroCommand(partyOff);

		remoteControl.setCommand(0, partyOnCommand, partyOffCommand);

		System.out.println(remoteControl);

		remoteControl.onButtonWasPushed(0);
		remoteControl.onButtonWasPushed(0);

		remoteControl.undoButtonWasPushed();
		System.out.println(remoteControl);
		
	}

}
