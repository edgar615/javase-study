package com.edgar.designpattern.payroll.affiliations;

import com.edgar.designpattern.payroll.classifications.HourlyClassification;
import com.edgar.designpattern.payroll.classifications.TimeCard;
import com.edgar.designpattern.payroll.payrolldatabase.PayrollDatabase;
import com.edgar.designpattern.payroll.payrolldomain.Employee;
import com.edgar.designpattern.payroll.payrolldomain.Transaction;

public class TimeCardTransaction implements Transaction {

	private String date;

	private double hours;

	private String empId;

	@Override
	public void execute() {
		Employee e = PayrollDatabase.getEmployee(empId);
		HourlyClassification hc = (HourlyClassification) e.getClassification();
		hc.addTimeCard(new TimeCard(date, hours));
	}

    public TimeCardTransaction(String date, double hours, String empId) {
        this.date = date;
        this.hours = hours;
        this.empId = empId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
}
