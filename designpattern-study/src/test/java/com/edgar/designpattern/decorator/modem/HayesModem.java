package com.edgar.designpattern.decorator.modem;

public class HayesModem implements Modem {

	private String phoneNumber;
	private int speakerVolume;

	@Override
	public void dial(String pno) {
		phoneNumber = pno;
	}

	@Override
	public void setSpeakerVolume(int volume) {
		speakerVolume = volume;
	}

	@Override
	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Override
	public int getSpeakerVolume() {
		return speakerVolume;
	}

}
