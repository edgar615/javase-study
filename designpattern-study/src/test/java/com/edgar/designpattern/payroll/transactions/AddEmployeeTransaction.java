package com.edgar.designpattern.payroll.transactions;

import com.edgar.designpattern.payroll.methods.HoldMethod;
import com.edgar.designpattern.payroll.payrolldatabase.PayrollDatabase;
import com.edgar.designpattern.payroll.payrolldomain.*;

public abstract class AddEmployeeTransaction implements Transaction {

	private String empId;
	private String name;
	private String address;

	public AddEmployeeTransaction() {
		super();
	}

	public AddEmployeeTransaction(String empId, String name, String address) {
		super();
		this.empId = empId;
		this.name = name;
		this.address = address;
	}

	@Override
	public void execute() {
		PaymentClassification pc = getClassification();
		PaymentSchedule ps = getSchedule();
		PaymentMethod pm = new HoldMethod();
		Employee e = new Employee(empId, name, address);
		e.setClassification(pc);
		e.setSchedule(ps);
		e.setMethod(pm);
		PayrollDatabase.addEmployee(empId, e);
	}

	public abstract PaymentClassification getClassification();

	public abstract PaymentSchedule getSchedule();

}
