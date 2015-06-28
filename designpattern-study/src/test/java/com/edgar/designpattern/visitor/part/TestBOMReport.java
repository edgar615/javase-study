package com.edgar.designpattern.visitor.part;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestBOMReport {

	private PiecePart p1;
	private PiecePart p2;
	private Assembly a;

	@Before
	public void setUp() {
		p1 = new PiecePart("997624", "MyPart", 3.20);
		p2 = new PiecePart("7734", "Hell", 666);
		a = new Assembly("5879", "MyAssembly");
	}

	@Test
	public void testCreatePart() {
		Assert.assertEquals("997624", p1.getPartNumber());
		Assert.assertEquals("MyPart", p1.getDescription());
		Assert.assertEquals(3.20, p1.getCost(), .01);
	}
	
	@Test
	public void testInsertAssembly() {
		Assert.assertEquals("5879", a.getPartNumber());
		Assert.assertEquals("5879", a.getPartNumber());
		
	}
}
