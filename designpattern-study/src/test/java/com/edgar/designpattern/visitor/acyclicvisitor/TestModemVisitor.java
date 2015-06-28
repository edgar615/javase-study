package com.edgar.designpattern.visitor.acyclicvisitor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestModemVisitor {

	private HayesModem h;
	private ZoomModem z;
	private ErnieModem e;

	@Before
	public void setUp() {
		h = new HayesModem();
		z = new ZoomModem();
		e = new ErnieModem();
	}

	@Test
	public void testHayesForUnix() {
		h.accept(new HayesModemVisitor());
		Assert.assertEquals("&s1=4&D=3", h.configurationString);
	}

	@Test
	public void testZoomForUnix() {
		z.accept(new ZoomModemVisitor());
		Assert.assertEquals(42, z.configurationValue);
	}

	@Test
	public void testErnieForUnix() {
		e.accept(new ErnieModemVisitor());
		Assert.assertEquals("C is too slow", e.configurationPattern);
	}
}
