package com.edgar.designpattern.nullobject;

import junit.framework.TestCase;
import org.junit.Assert;

import java.util.Date;


public class TestEmployee extends TestCase{

	public void testNull() {
		Employee e = DB.getEmployee("Bob");
		if (e.isTimeToPay(new Date())) {
			fail();
		}
		Assert.assertEquals(Employee.NULL, e);
	}
}
