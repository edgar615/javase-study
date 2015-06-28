package com.edgar.designpattern.payroll.classifications;

import com.edgar.designpattern.payroll.payrolldomain.Paycheck;
import com.edgar.designpattern.payroll.payrolldomain.PaymentClassification;

public class SalariedClassification implements PaymentClassification {

	private double salary;

	public SalariedClassification(double salary) {
		super();
		this.salary = salary;
	}

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
	public double calculatePay(Paycheck pc) {
		return salary;
	}

}
