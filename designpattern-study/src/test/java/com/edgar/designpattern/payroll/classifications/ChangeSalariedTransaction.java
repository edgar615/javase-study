package com.edgar.designpattern.payroll.classifications;

import com.edgar.designpattern.payroll.payrolldomain.PaymentClassification;
import com.edgar.designpattern.payroll.payrolldomain.PaymentSchedule;
import com.edgar.designpattern.payroll.schedules.MonthlySchedule;

public class ChangeSalariedTransaction extends ChangeClassificationTransaction {

	private double salary;

	public ChangeSalariedTransaction(String empId, double salary) {
		super(empId);
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
