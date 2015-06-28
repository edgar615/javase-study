package com.edgar.designpattern.command;

import com.edgar.designpattern.command.door.GarageDoor;
import com.edgar.designpattern.command.door.GarageDoorDownCommand;
import com.edgar.designpattern.command.door.GarageDoorUpCommand;
import com.edgar.designpattern.command.fan.CeilingFan;
import com.edgar.designpattern.command.fan.CeilingFanHighCommand;
import com.edgar.designpattern.command.fan.CeilingFanMediumCommand;
import com.edgar.designpattern.command.fan.CeilingFanOffCommand;
import com.edgar.designpattern.command.light.Light;
import com.edgar.designpattern.command.light.LightOffCommand;
import com.edgar.designpattern.command.light.LightOnCommand;
import com.edgar.designpattern.command.stereo.Stereo;
import com.edgar.designpattern.command.stereo.StereoOffCommand;
import com.edgar.designpattern.command.stereo.StereoOnWithCDCommand;

public class RemoteLoader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RemoteControl remoteControl = new RemoteControl();

		Light livingRoomLight = new Light("Living Room");
		Light kitchenLight = new Light("Kitchen");
		GarageDoor garageDoor = new GarageDoor("");
		Stereo stereo = new Stereo("Living Room");
		CeilingFan ceilingFan = new CeilingFan("Living Room");

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

		CeilingFanHighCommand ceilingFanHigh = new CeilingFanHighCommand(
				ceilingFan);
		CeilingFanMediumCommand ceilingFanMedium = new CeilingFanMediumCommand(
				ceilingFan);
		CeilingFanOffCommand ceilingFanOff = new CeilingFanOffCommand(
				ceilingFan);

		remoteControl.setCommand(0, livingRoomLightOn, livingRoomLightOff);
		remoteControl.setCommand(1, kitchenLightOn, kitchenLightOff);
		remoteControl.setCommand(2, garageDoorUp, garageDoorDown);
		remoteControl.setCommand(3, stereoOnWithCD, stereoOff);
		remoteControl.setCommand(4, ceilingFanMedium, ceilingFanOff);
		remoteControl.setCommand(5, ceilingFanHigh, ceilingFanOff);

		System.out.println(remoteControl);

		remoteControl.onButtonWasPushed(0);
		remoteControl.onButtonWasPushed(0);
		remoteControl.onButtonWasPushed(1);
		remoteControl.onButtonWasPushed(1);
		remoteControl.onButtonWasPushed(2);
		remoteControl.onButtonWasPushed(2);
		remoteControl.onButtonWasPushed(3);
		remoteControl.onButtonWasPushed(3);

		remoteControl.undoButtonWasPushed();
		System.out.println(remoteControl);
		
		remoteControl.onButtonWasPushed(4);
		remoteControl.offButtonWasPushed(4);
		System.out.println(remoteControl);
		remoteControl.undoButtonWasPushed();
		
		remoteControl.onButtonWasPushed(5);
		System.out.println(remoteControl);
		remoteControl.undoButtonWasPushed();
	}

}
