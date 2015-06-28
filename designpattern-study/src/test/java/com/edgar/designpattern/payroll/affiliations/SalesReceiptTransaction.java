package com.edgar.designpattern.payroll.affiliations;

import com.edgar.designpattern.payroll.classifications.CommisionClassification;
import com.edgar.designpattern.payroll.classifications.SalesReceipt;
import com.edgar.designpattern.payroll.payrolldatabase.PayrollDatabase;
import com.edgar.designpattern.payroll.payrolldomain.Employee;
import com.edgar.designpattern.payroll.payrolldomain.Transaction;


public class SalesReceiptTransaction implements Transaction {

	private String date;

	private double amount;

	private String empId;

	@Override
	public void execute() {
		Employee employee = PayrollDatabase.getEmployee(empId);
		CommisionClassification cc = (CommisionClassification) employee
				.getClassification();
		cc.addSalesReceipt(new SalesReceipt(date, amount));
	}

    public SalesReceiptTransaction(String date, double amount, String empId) {
        this.date = date;
        this.amount = amount;
        this.empId = empId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
}
