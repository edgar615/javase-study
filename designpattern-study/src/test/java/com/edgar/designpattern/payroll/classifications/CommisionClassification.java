package com.edgar.designpattern.payroll.classifications;

import com.edgar.designpattern.payroll.payrolldomain.Paycheck;
import com.edgar.designpattern.payroll.payrolldomain.PaymentClassification;

import java.util.HashMap;
import java.util.Map;

public class CommisionClassification implements PaymentClassification {

	private double salary;

	private double commissionRate;

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(double commissionRate) {
        this.commissionRate = commissionRate;
    }

    public Map<String, SalesReceipt> getSalesReceiptMap() {
        return salesReceiptMap;
    }

    private final Map<String, SalesReceipt> salesReceiptMap = new HashMap<String, SalesReceipt>();

	public CommisionClassification(double salary, double commissionRate) {
		super();
		this.salary = salary;
		this.commissionRate = commissionRate;
	}

	public SalesReceipt getSalesReceipt(String date) {
		return salesReceiptMap.get(date);
	}

	public void addSalesReceipt(SalesReceipt salesReceipt) {
		salesReceiptMap.put(salesReceipt.getDate(), salesReceipt);
	}

	@Override
	public double calculatePay(Paycheck pc) {
		double sum = salary;
		for (SalesReceipt salesReceipt : salesReceiptMap.values()) {
			sum += salesReceipt.getAmount() * commissionRate;
		}
		return sum;
	}
}
