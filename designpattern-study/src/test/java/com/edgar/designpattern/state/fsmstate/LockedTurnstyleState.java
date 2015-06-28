package com.edgar.designpattern.state.fsmstate;

class LockedTurnstyleState implements TurnstyleState {
	public void coin(Turnstyle t) {
		t.setUnlocked();
		t.unlock();
	}

	public void pass(Turnstyle t) {
		t.alarm();
	}
}