package com.edgar.designpattern.payroll.classifications;

import com.edgar.designpattern.payroll.payrolldomain.PaymentClassification;
import com.edgar.designpattern.payroll.payrolldomain.PaymentSchedule;
import com.edgar.designpattern.payroll.schedules.BlweeklySchedule;
import com.edgar.designpattern.payroll.transactions.AddEmployeeTransaction;

public class AddCommissionEmployee extends AddEmployeeTransaction {
	
	private double salary;
	
	private double commissionRate;
	
	public AddCommissionEmployee(String empId, String name, String address,
			double salary, double commissionRate) {
		super(empId, name, address);
		this.salary = salary;
		this.commissionRate = commissionRate;
	}


	@Override
	public PaymentClassification getClassification() {
		return new CommisionClassification(salary, commissionRate);
	}

	@Override
	public PaymentSchedule getSchedule() {
		return new BlweeklySchedule();
	}

}
