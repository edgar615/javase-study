package com.edgar.designpattern.visitor.visitor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestModemVisitor {

	private UnixModemCofnigurator v;
	private HayesModem h;
	private ZoomModem z;
	private ErnieModem e;

	@Before
	public void setUp() {
		v = new UnixModemCofnigurator();
		h = new HayesModem();
		z = new ZoomModem();
		e = new ErnieModem();
	}

	@Test
	public void testHayesForUnix() {
		h.accept(v);
		Assert.assertEquals("&s1=4&D=3", h.configurationString);
	}

	@Test
	public void testZoomForUnix() {
		z.accept(v);
		Assert.assertEquals(42, z.configurationValue);
	}

	@Test
	public void testErnieForUnix() {
		e.accept(v);
		Assert.assertEquals("C is too slow", e.configurationPattern);
	}
}
