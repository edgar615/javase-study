package com.edgar.designpattern.decorator.modem;

import org.junit.Assert;
import org.junit.Test;

public class ModemDecoratorTest {

	@Test
	public void testCreateHays() {
		Modem m = new HayesModem();
		Assert.assertEquals(null, m.getPhoneNumber());
		m.dial("5551212");
		Assert.assertEquals("5551212", m.getPhoneNumber());
		Assert.assertEquals(0, m.getSpeakerVolume());
		m.setSpeakerVolume(10);
		Assert.assertEquals(10, m.getSpeakerVolume());

	}

	@Test
	public void testCreateLoudDialModem() {
		Modem m = new HayesModem();
		Modem d = new LoudDialModem(m);
		Assert.assertEquals(null, d.getPhoneNumber());
		Assert.assertEquals(0, d.getSpeakerVolume());
		d.dial("5551212");
		Assert.assertEquals("5551212", d.getPhoneNumber());
		Assert.assertEquals(10, d.getSpeakerVolume());

	}
}
