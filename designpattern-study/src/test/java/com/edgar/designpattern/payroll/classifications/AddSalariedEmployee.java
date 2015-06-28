package com.edgar.designpattern.payroll.classifications;

import com.edgar.designpattern.payroll.payrolldomain.PaymentClassification;
import com.edgar.designpattern.payroll.payrolldomain.PaymentSchedule;
import com.edgar.designpattern.payroll.schedules.MonthlySchedule;
import com.edgar.designpattern.payroll.transactions.AddEmployeeTransaction;


public class AddSalariedEmployee extends AddEmployeeTransaction {

	private double salary;

	public AddSalariedEmployee(String empId, String name, String address,
			double salary) {
		super(empId, name, address);
		this.salary = salary;
	}

	@Override
	public PaymentClassification getClassification() {
		return new SalariedClassification(salary);
	}

	@Override
	public PaymentSchedule getSchedule() {
		return new MonthlySchedule();
	}
}
