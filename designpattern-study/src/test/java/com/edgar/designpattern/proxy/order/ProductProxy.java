package com.edgar.designpattern.proxy.order;

import java.sql.SQLException;

public class ProductProxy implements Product {

	private String sku;

	public ProductProxy(String sku) {
		super();
		this.sku = sku;
	}

	@Override
	public int getPrice() throws SQLException {
		ProductData pd = DB.getProductData(sku);
		return pd.getPrice();
	}

	@Override
	public String getName() throws SQLException {
		ProductData pd = DB.getProductData(sku);
		return pd.getName();
	}

	@Override
	public String getSku() {
		return sku;
	}

}
