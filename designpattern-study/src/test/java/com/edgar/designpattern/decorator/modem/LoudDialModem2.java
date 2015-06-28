package com.edgar.designpattern.decorator.modem;

public class LoudDialModem2 extends ModemDecorator {

	public LoudDialModem2(Modem modem) {
		super(modem);
	}

	@Override
	public void dial(String pno) {
		getModem().setSpeakerVolume(10);
		getModem().dial(pno);
	}

}
