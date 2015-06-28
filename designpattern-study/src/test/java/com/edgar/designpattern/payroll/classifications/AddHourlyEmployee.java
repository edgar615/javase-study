package com.edgar.designpattern.payroll.classifications;

import com.edgar.designpattern.payroll.payrolldomain.PaymentClassification;
import com.edgar.designpattern.payroll.payrolldomain.PaymentSchedule;
import com.edgar.designpattern.payroll.schedules.WeeklySchedule;
import com.edgar.designpattern.payroll.transactions.AddEmployeeTransaction;

public class AddHourlyEmployee extends AddEmployeeTransaction {

	private double hourlyRate;

	public AddHourlyEmployee(String empId, String name, String address,
			double hourlyRate) {
		super(empId, name, address);
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
