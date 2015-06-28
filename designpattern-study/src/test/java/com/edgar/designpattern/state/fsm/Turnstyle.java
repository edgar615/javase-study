package com.edgar.designpattern.state.fsm;

public class Turnstyle {

	public static final int LOCKED = 0;
	public static final int UNLOCKED = 1;
	public static final int COIN = 0;
	public static final int PASS = 1;

	int state = LOCKED;

	private TurnstyleController turnstileController;

	public Turnstyle(TurnstyleController turnstileController) {
		super();
		this.turnstileController = turnstileController;
	}

	public void event(int event) {
		switch (state) {
		case LOCKED:
			switch (event) {
			case COIN:
				state = UNLOCKED;
				turnstileController.unlock();
				break;
			case PASS:
				turnstileController.alarm();
				break;
			}
			break;
		case UNLOCKED:
			switch (event) {
			case COIN:
				turnstileController.thankyou();
				break;
			case PASS:
				state = LOCKED;
				turnstileController.lock();
				break;
			}
			break;
		}
	}
}
