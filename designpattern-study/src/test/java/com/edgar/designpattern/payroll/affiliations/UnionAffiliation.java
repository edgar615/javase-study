package com.edgar.designpattern.payroll.affiliations;

import com.edgar.designpattern.payroll.payrolldomain.Affiliation;
import com.edgar.designpattern.payroll.payrolldomain.Paycheck;

import java.util.HashMap;
import java.util.Map;

public class UnionAffiliation implements Affiliation {

	private String memberId;

	private double dues;

    public UnionAffiliation(String memberId, double dues) {
        this.memberId = memberId;
        this.dues = dues;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public double getDues() {
        return dues;
    }

    public void setDues(double dues) {
        this.dues = dues;
    }

    public Map<String, ServiceCharge> getServiceCharges() {
        return serviceCharges;
    }

    private final Map<String, ServiceCharge> serviceCharges = new HashMap<String, ServiceCharge>();

	public ServiceCharge getServiceCharge(String date) {
		return serviceCharges.get(date);
	}

	public void addServiceCharge(String date, double charge) {
		serviceCharges.put(date, new ServiceCharge(date, charge));
	}

	@Override
	public double calculatePay(Paycheck pc) {
		return 0;
	}
}
