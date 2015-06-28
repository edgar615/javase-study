package com.edgar.designpattern.payroll.affiliations;

import com.edgar.designpattern.payroll.payrolldomain.Affiliation;
import com.edgar.designpattern.payroll.payrolldomain.Paycheck;

public class NoAffiliation implements Affiliation {

	@Override
	public double calculatePay(Paycheck pc) {
		// TODO Auto-generated method stub
		return 0;
	}

}
