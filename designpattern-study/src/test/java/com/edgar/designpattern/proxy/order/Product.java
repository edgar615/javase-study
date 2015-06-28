package com.edgar.designpattern.proxy.order;

import java.sql.SQLException;


public interface Product {

	int getPrice() throws SQLException;

	String getName() throws SQLException;

	String getSku();
}
