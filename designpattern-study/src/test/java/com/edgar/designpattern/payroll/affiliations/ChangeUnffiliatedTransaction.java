package com.edgar.designpattern.payroll.affiliations;

import com.edgar.designpattern.payroll.payrolldatabase.PayrollDatabase;
import com.edgar.designpattern.payroll.payrolldomain.Affiliation;
import com.edgar.designpattern.payroll.payrolldomain.Employee;

public class ChangeUnffiliatedTransaction extends ChangeAffiliationTransaction {

	public ChangeUnffiliatedTransaction(String empId) {
		super(empId);
	}

	@Override
	public Affiliation getAffiliation() {
		return new NoAffiliation();
	}

	@Override
	public void recordMembership(Employee e) {
		Affiliation af = e.getAffiliation();
		UnionAffiliation uf = (UnionAffiliation) af;
		String memeberId = uf.getMemberId();
		PayrollDatabase.removeUnionMember(memeberId);
	}

}
