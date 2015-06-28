package com.edgar.designpattern.proxy.order;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class ProxyTest {

	@Before
	public void setUp() throws Exception {
		DB.init();
		ProductData pd = new ProductData("ProxyTestName", 456, "ProxtTest1");
		DB.store(pd);
	}

	@After
	public void tearDown() throws Exception {
		DB.deleteProductData("ProxtTest1");
		DB.close();
	}

	@Test
	public void testProductProxy() throws SQLException {
		Product p = new ProductProxy("ProxyTest1");
		Assert.assertEquals(456, p.getPrice());
		Assert.assertEquals("ProxyTestName1", p.getName());
		Assert.assertEquals("ProxyTest1", p.getSku());
	}

	@Test
	public void testOrderProxyTotal() throws Exception {
		DB.store(new ProductData("Wheaties", 349, "wheaties"));
		DB.store(new ProductData("Crest", 258, "crest"));
		ProductProxy wheaties = new ProductProxy("wheaties");
		ProductProxy crest = new ProductProxy("crest");
		OrderData od = DB.newOrder("testOrderProxy");
		OrderProxy order = new OrderProxy(od.orderId);
		order.addItem(crest, 1);
		order.addItem(wheaties, 2);
		Assert.assertEquals(956, order.total());
	}
}
