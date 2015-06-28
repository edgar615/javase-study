package com.edgar.designpattern.proxy.order;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class DBTest {

	@Before
	public void setUp() throws Exception {
		DB.init();
	}
	
	@After
	public void tearDown() throws Exception {
		DB.close();
	}
	
	@Test
	public void testStoreProduct() throws Exception {
		ProductData storedProduct = new ProductData("MyProduct", 1234, "999");
		DB.store(storedProduct);
		ProductData retrievedProduct = DB.getProductData("999");
		DB.deleteProductData("999");
		Assert.assertEquals(storedProduct, retrievedProduct);
	}
}
