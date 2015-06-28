package com.edgar.designpattern.isp.door;

public class DoorTimeAdapter implements TimerClient {
	
	private TimedDoor1 door;

	private DoorTimeAdapter(TimedDoor1 door) {
		super();
		this.door = door;
	}

	@Override
	public void timeOut() {
		// TODO Auto-generated method stub

	}

}
