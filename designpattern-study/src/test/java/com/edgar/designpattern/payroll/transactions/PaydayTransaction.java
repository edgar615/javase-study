package com.edgar.designpattern.payroll.transactions;

import com.edgar.designpattern.payroll.payrolldatabase.PayrollDatabase;
import com.edgar.designpattern.payroll.payrolldomain.Employee;
import com.edgar.designpattern.payroll.payrolldomain.Paycheck;
import com.edgar.designpattern.payroll.payrolldomain.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaydayTransaction implements Transaction {

	private String payDate;

	private Map<String, Paycheck> payChecks = new HashMap<String, Paycheck>();

	public PaydayTransaction(String payDate) {
		super();
		this.payDate = payDate;
	}

	@Override
	public void execute() {
		List<String> empIds = PayrollDatabase.getEmployeeIds();
		for (String empId : empIds) {
			Employee e = PayrollDatabase.getEmployee(empId);
			if (e.isPayDate(payDate)) {
				Paycheck pc = new Paycheck(payDate);
				payChecks.put(empId, pc);
				e.payday(pc);
			}
		}
	}

	public Paycheck getPayCheck(String empId) {
		return payChecks.get(empId);
	}

}
