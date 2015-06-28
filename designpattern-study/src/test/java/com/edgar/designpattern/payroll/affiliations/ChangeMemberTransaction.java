package com.edgar.designpattern.payroll.affiliations;

import com.edgar.designpattern.payroll.payrolldatabase.PayrollDatabase;
import com.edgar.designpattern.payroll.payrolldomain.Affiliation;
import com.edgar.designpattern.payroll.payrolldomain.Employee;

public class ChangeMemberTransaction extends ChangeAffiliationTransaction {

	private String memberId;
	private double dues;

	public ChangeMemberTransaction(String empId, String memberId, double dues) {
		super(empId);
		this.memberId = memberId;
		this.dues = dues;
	}

	@Override
	public void change(Employee e) {
		recordMembership(e);
		e.setAffiliation(getAffiliation());
	}

	@Override
	public Affiliation getAffiliation() {
		return new UnionAffiliation(memberId, dues);
	}

	@Override
	public void recordMembership(Employee e) {
		PayrollDatabase.addUnionMember(memberId, e);
	}

}
