package com.edgar.designpattern.state.fsm;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestTurnstyle {

	private Turnstyle t;
	private boolean lockCalled = false;
	private boolean unlockCalled = false;
	private boolean thankyouCalled = false;
	private boolean alarmCalled = false;

	@Before
	public void setUp() {
		TurnstyleController controllerSpoof = new TurnstyleController() {
			public void lock() {
				lockCalled = true;
			}

			public void unlock() {
				unlockCalled = true;
			}

			public void thankyou() {
				thankyouCalled = true;
			}

			public void alarm() {
				alarmCalled = true;
			}
		};

		t = new Turnstyle(controllerSpoof);
	}

	@Test
	public void testInitialConditions() {
		Assert.assertEquals(Turnstyle.LOCKED, t.state);
	}

	@Test
	public void testCoinInLockedState() {
		t.state = Turnstyle.LOCKED;
		t.event(Turnstyle.COIN);
		Assert.assertEquals(Turnstyle.UNLOCKED, t.state);
		assert (unlockCalled);
	}

	@Test
	public void testCoinInUnlockedState() {
		t.state = Turnstyle.UNLOCKED;
		t.event(Turnstyle.COIN);
		Assert.assertEquals(Turnstyle.UNLOCKED, t.state);
		assert (thankyouCalled);
	}

	@Test
	public void testPassInLockedState() {
		t.state = Turnstyle.LOCKED;
		t.event(Turnstyle.PASS);
		Assert.assertEquals(Turnstyle.LOCKED, t.state);
		assert (alarmCalled);
	}

	@Test
	public void testPassInUnlockedState() {
		t.state = Turnstyle.UNLOCKED;
		t.event(Turnstyle.PASS);
		Assert.assertEquals(Turnstyle.LOCKED, t.state);
		assert (lockCalled);
	}
}