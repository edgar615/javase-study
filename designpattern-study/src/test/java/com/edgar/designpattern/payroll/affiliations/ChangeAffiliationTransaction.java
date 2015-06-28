package com.edgar.designpattern.payroll.affiliations;

import com.edgar.designpattern.payroll.payrolldomain.Affiliation;
import com.edgar.designpattern.payroll.payrolldomain.Employee;
import com.edgar.designpattern.payroll.payrolldomain.Transaction;
import com.edgar.designpattern.payroll.transactions.ChangeEmployeeTransaction;

public abstract class ChangeAffiliationTransaction extends
		ChangeEmployeeTransaction implements Transaction {

	public ChangeAffiliationTransaction(String empId) {
		super(empId);
	}

	@Override
	public void change(Employee e) {
		recordMembership(e);
		e.setAffiliation(getAffiliation());
	}

	public abstract Affiliation getAffiliation();

	public abstract void recordMembership(Employee e);
}
