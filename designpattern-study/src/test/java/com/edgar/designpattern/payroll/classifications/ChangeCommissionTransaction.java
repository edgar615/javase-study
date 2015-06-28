package com.edgar.designpattern.payroll.classifications;

import com.edgar.designpattern.payroll.payrolldomain.PaymentClassification;
import com.edgar.designpattern.payroll.payrolldomain.PaymentSchedule;
import com.edgar.designpattern.payroll.schedules.BlweeklySchedule;

public class ChangeCommissionTransaction extends
		ChangeClassificationTransaction {

	private double salary;

	private double commissionRate;

	public ChangeCommissionTransaction(String empId, double salary,
			double commissionRate) {
		super(empId);
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
