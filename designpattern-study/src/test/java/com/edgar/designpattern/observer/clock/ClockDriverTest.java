package com.edgar.designpattern.observer.clock;

import org.junit.Assert;
import org.junit.Test;

public class ClockDriverTest {

	@Test
	public void testTimeChange() {
		MockTimeSource source = new MockTimeSource();
		MockTimeSink sink = new MockTimeSink(source);
		source.registerObserver(sink);
		source.setTime(3, 4, 5);
		Assert.assertEquals(3, sink.getHours());
		Assert.assertEquals(4, sink.getMinutes());
		Assert.assertEquals(5, sink.getSeconds());
		source.setTime(7, 8, 9);
		Assert.assertEquals(7, sink.getHours());
		Assert.assertEquals(8, sink.getMinutes());
		Assert.assertEquals(9, sink.getSeconds());
	}
}
