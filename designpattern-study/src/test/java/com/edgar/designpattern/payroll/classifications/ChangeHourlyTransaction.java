package com.edgar.designpattern.payroll.classifications;

import com.edgar.designpattern.payroll.payrolldomain.PaymentClassification;
import com.edgar.designpattern.payroll.payrolldomain.PaymentSchedule;
import com.edgar.designpattern.payroll.schedules.WeeklySchedule;

public class ChangeHourlyTransaction extends ChangeClassificationTransaction {

	private double hourlyRate;

	public ChangeHourlyTransaction(String empId, double hourlyRate) {
		super(empId);
		this.hourlyRate = hourlyRate;
	}

	@Override
	public PaymentClassification getClassification() {
		return new HourlyClassification(hourlyRate);
	}

	@Override
	public PaymentSchedule getSchedule() {
		return new WeeklySchedule();
	}
}
