package com.edgar.designpattern.state.fsmstate;

class UnlockedTurnstyleState implements TurnstyleState {
	public void coin(Turnstyle t) {
		t.thankyou();
	}

	public void pass(Turnstyle t) {
		t.setLocked();
		t.lock();
	}
}