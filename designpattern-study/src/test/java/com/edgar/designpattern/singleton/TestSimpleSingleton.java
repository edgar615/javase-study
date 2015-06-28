package com.edgar.designpattern.singleton;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;

public class TestSimpleSingleton {

	@Test
	public void testCreateSingleton() {
		Singleton s1 = Singleton.getInstance();
		Singleton s2 = Singleton.getInstance();
		Assert.assertSame(s1, s2);
	}

	@Test
	public void testNoPublicConstructors() throws ClassNotFoundException {
		Class singleton = Class
				.forName("com.edgar.designpattern.singleton.Singleton");
		Constructor[] constructors = singleton.getConstructors();
		Assert.assertEquals(0, constructors.length);
	}
}
