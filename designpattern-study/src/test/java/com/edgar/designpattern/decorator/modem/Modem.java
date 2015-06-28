package com.edgar.designpattern.decorator.modem;

public interface Modem {

	void dial(String pno);

	void setSpeakerVolume(int volume);

	String getPhoneNumber();

	int getSpeakerVolume();
}
