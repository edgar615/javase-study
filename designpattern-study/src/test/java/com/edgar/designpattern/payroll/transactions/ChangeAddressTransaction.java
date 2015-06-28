package com.edgar.designpattern.payroll.transactions;

import com.edgar.designpattern.payroll.payrolldomain.Employee;

public class ChangeAddressTransaction extends ChangeEmployeeTransaction {

	private String address;

	public ChangeAddressTransaction(String empId, String address) {
		super(empId);
		this.address = address;
	}

	@Override
	public void change(Employee e) {
		e.setAddress(address);
	}

}
