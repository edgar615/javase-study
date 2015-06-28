package com.edgar.designpattern.payroll.affiliations;

import com.edgar.designpattern.payroll.payrolldatabase.PayrollDatabase;
import com.edgar.designpattern.payroll.payrolldomain.Affiliation;
import com.edgar.designpattern.payroll.payrolldomain.Employee;
import com.edgar.designpattern.payroll.payrolldomain.Transaction;

public class ServiceChargeTransaction implements Transaction {

	private String memberId;
	private String date;
	private double charge;

	@Override
	public void execute() {
		Employee employee = PayrollDatabase.getUnionMember(memberId);
		Affiliation af = employee.getAffiliation();
		UnionAffiliation uaf = (UnionAffiliation) af;
		uaf.addServiceCharge(date, charge);
	}


    public ServiceChargeTransaction(String memberId, String date, double charge) {
        this.memberId = memberId;
        this.date = date;
        this.charge = charge;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }
}
