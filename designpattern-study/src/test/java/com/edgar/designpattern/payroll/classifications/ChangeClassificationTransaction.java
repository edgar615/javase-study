package com.edgar.designpattern.payroll.classifications;

import com.edgar.designpattern.payroll.payrolldomain.Employee;
import com.edgar.designpattern.payroll.payrolldomain.PaymentClassification;
import com.edgar.designpattern.payroll.payrolldomain.PaymentSchedule;
import com.edgar.designpattern.payroll.transactions.ChangeEmployeeTransaction;


public abstract class ChangeClassificationTransaction extends
		ChangeEmployeeTransaction {

	public ChangeClassificationTransaction(String empId) {
		super(empId);
	}

	@Override
	public void change(Employee e) {
		e.setClassification(getClassification());
		e.setSchedule(getSchedule());
	}

	public abstract PaymentClassification getClassification();

	public abstract PaymentSchedule getSchedule();

}
