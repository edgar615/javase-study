package com.edgar.designpattern.payroll.transactions;

import com.edgar.designpattern.payroll.payrolldatabase.PayrollDatabase;
import com.edgar.designpattern.payroll.payrolldomain.Transaction;

public class DeleteEmployeeTransaction implements Transaction {

	private String empId;
	
	public DeleteEmployeeTransaction(String empId) {
		super();
		this.empId = empId;
	}

	@Override
	public void execute() {
		PayrollDatabase.deleteEmployee(empId);
	}

}
