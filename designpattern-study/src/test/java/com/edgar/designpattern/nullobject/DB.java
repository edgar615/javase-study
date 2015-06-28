package com.edgar.designpattern.nullobject;

public class DB {

	public static Employee getEmployee(String name) {
		return Employee.NULL;
	}
}
