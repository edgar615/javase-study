package com.edgar.designpattern.decorator.modem;

public class ModemDecorator implements Modem {

	private Modem modem;

	public ModemDecorator(Modem modem) {
		super();
		this.modem = modem;
	}

	@Override
	public void dial(String pno) {
		modem.setSpeakerVolume(10);
		modem.dial(pno);
	}

	@Override
	public void setSpeakerVolume(int volume) {
		modem.setSpeakerVolume(volume);
	}

	@Override
	public String getPhoneNumber() {
		return modem.getPhoneNumber();
	}

	@Override
	public int getSpeakerVolume() {
		return modem.getSpeakerVolume();
	}

	public Modem getModem() {
		return modem;
	}

}
