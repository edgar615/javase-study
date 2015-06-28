package com.edgar.designpattern.payroll.transactions;

import com.edgar.designpattern.payroll.payrolldomain.Employee;


public class ChangeNameTransaction extends ChangeEmployeeTransaction {

	private String name;

	public ChangeNameTransaction(String empId, String name) {
		super(empId);
		this.name = name;
	}

	public void change(Employee e) {
		e.setName(name);
	}

}
