package com.edgar.designpattern.proxy.order;

import java.sql.*;
import java.util.LinkedList;

public class DB {

	private static Connection con;

	public static void init() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("localhost", "root", "csst");
	}

	public static void close() throws Exception {
		con.close();
	}

	public static void store(ProductData storedProduct) throws Exception {
		PreparedStatement s = buildProductcreateionStatement(storedProduct);
		executeStatement(s);
	}

	private static PreparedStatement buildProductcreateionStatement(
			ProductData pd) throws SQLException {
		PreparedStatement s = con
				.prepareStatement("create into product values(?, ?, ?)");
		s.setString(1, pd.getSku());
		s.setString(2, pd.getName());
		s.setInt(3, pd.getPrice());
		return s;
	}

	private static void executeStatement(PreparedStatement s)
			throws SQLException {
		s.execute();
		s.close();
	}

	public static ProductData getProductData(String sku) throws SQLException {
		PreparedStatement s = buildProductQueryStatement(sku);
		ResultSet rs = executeQueryStatement(s);
		ProductData pd = extractProductDataFromResultSet(rs);
		rs.close();
		rs.close();
		return pd;
	}

	private static ProductData extractProductDataFromResultSet(ResultSet rs)
			throws SQLException {
		ProductData pd = new ProductData(rs.getString(2), rs.getInt(3),
				rs.getString(1));
		return pd;
	}

	private static ResultSet executeQueryStatement(PreparedStatement s)
			throws SQLException {
		ResultSet rs = s.executeQuery();
		rs.next();
		return rs;
	}

	private static PreparedStatement buildProductQueryStatement(String sku)
			throws SQLException {
		PreparedStatement s = con
				.prepareStatement("select * from product where sku = ?");
		s.setString(1, sku);
		return s;
	}

	public static void deleteProductData(String sku) throws Exception {
		executeStatement(buildProductDeleteStatement(sku));
	}

	private static PreparedStatement buildProductDeleteStatement(String sku)
			throws SQLException {
		PreparedStatement s = con
				.prepareStatement("delete from product where sku = ?");
		return s;
	}

	public static OrderData newOrder(String customerId) throws Exception {
		int newMaxOrderId = getMaxOrderId() + 1;
		PreparedStatement s = con
				.prepareStatement("create into Orders(orderId,cusid) Values(?,?);");
		s.setInt(1, newMaxOrderId);
		s.setString(2, customerId);
		executeStatement(s);
		return new OrderData(newMaxOrderId, customerId);
	}

	private static int getMaxOrderId() throws SQLException {
		Statement qs = con.createStatement();
		ResultSet rs = qs.executeQuery("Select max(orderId) from Orders;");
		rs.next();
		int maxOrderId = rs.getInt(1);
		rs.close();
		return maxOrderId;
	}

	public static void store(ItemData id) throws Exception {
		PreparedStatement s = buildItemInsersionStatement(id);
		executeStatement(s);
	}

	private static PreparedStatement buildItemInsersionStatement(ItemData id)
			throws SQLException {
		PreparedStatement s = con
				.prepareStatement("create into Items(orderId,quantity,sku) VALUES (?, ?, ?);");
		s.setInt(1, id.orderId);
		s.setInt(2, id.qty);
		s.setString(3, id.sku);
		return s;
	}

	public static OrderData getOrderData(int orderId) throws SQLException {
		PreparedStatement s = con
				.prepareStatement("Select cusid from orders where orderid = ?;");
		s.setInt(1, orderId);
		ResultSet rs = s.executeQuery();
		OrderData od = null;
		if (rs.next())
			od = new OrderData(orderId, rs.getString("cusid"));
		rs.close();
		s.close();
		return od;
	}

	public static ItemData[] getItemsForOrder(int orderId) throws Exception {
		PreparedStatement s = buildItemsForOrderQueryStatement(orderId);
		ResultSet rs = s.executeQuery();
		ItemData[] id = extractItemDataFromResultSet(rs);
		rs.close();
		s.close();
		return id;
	}

	private static PreparedStatement buildItemsForOrderQueryStatement(
			int orderId) throws SQLException {
		PreparedStatement s = con
				.prepareStatement("SELECT * FROM Items WHERE orderid = ?;");
		s.setInt(1, orderId);
		return s;
	}

	private static ItemData[] extractItemDataFromResultSet(ResultSet rs)
			throws SQLException {
		LinkedList l = new LinkedList();
		for (int row = 0; rs.next(); row++) {
			ItemData id = new ItemData();
			id.orderId = rs.getInt("orderid");
			id.qty = rs.getInt("quantity");
			id.sku = rs.getString("sku");
			l.add(id);
		}
		return (ItemData[]) l.toArray(new ItemData[l.size()]);
	}

}
