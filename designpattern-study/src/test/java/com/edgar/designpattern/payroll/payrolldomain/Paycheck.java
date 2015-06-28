package com.edgar.designpattern.payroll.payrolldomain;

import java.util.HashMap;
import java.util.Map;

public class Paycheck {

	private String payDate;
	private double grossPay;
	private double deductions;
	private double nextPay;
	private final Map<String, String> fields = new HashMap<String, String>();

	public Paycheck(String payDate) {
		super();
		this.payDate = payDate;
	}

	public String getField(String key) {
		return fields.get(key);
	}

	public void setField(String key, String value) {
		fields.put(key, value);
	}

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public double getGrossPay() {
        return grossPay;
    }

    public void setGrossPay(double grossPay) {
        this.grossPay = grossPay;
    }

    public double getDeductions() {
        return deductions;
    }

    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }

    public double getNextPay() {
        return nextPay;
    }

    public void setNextPay(double nextPay) {
        this.nextPay = nextPay;
    }

    public Map<String, String> getFields() {
        return fields;
    }
}
