package com.edgar.designpattern.payroll.payrolldomain;

import com.edgar.designpattern.payroll.affiliations.NoAffiliation;

public class Employee {
	private String empId;
	private String name;
	private String address;
	private PaymentClassification classification;
	private PaymentSchedule schedule;
	private PaymentMethod method;
	private Affiliation affiliation = new NoAffiliation();

	public Employee(String empId, String name, String address) {
		super();
		this.empId = empId;
		this.name = name;
		this.address = address;
	}

	public boolean isPayDate(String payDate) {
		return schedule.isPayDate(payDate);
	}

	public void payday(Paycheck pc) {
		double grossPay = classification.calculatePay(pc);
		double deductions = affiliation.calculatePay(pc);
		double nextPay = grossPay - deductions;
		pc.setGrossPay(grossPay);
		pc.setDeductions(deductions);
		pc.setNextPay(nextPay);
		method.pay(pc);
	}

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PaymentClassification getClassification() {
        return classification;
    }

    public void setClassification(PaymentClassification classification) {
        this.classification = classification;
    }

    public PaymentSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(PaymentSchedule schedule) {
        this.schedule = schedule;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public Affiliation getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(Affiliation affiliation) {
        this.affiliation = affiliation;
    }
}
