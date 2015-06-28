package com.edgar.designpattern.payroll.methods;

import com.edgar.designpattern.payroll.payrolldomain.Paycheck;
import com.edgar.designpattern.payroll.payrolldomain.PaymentMethod;

public class HoldMethod implements PaymentMethod {

	@Override
	public void pay(Paycheck pc) {
		 pc.setField("Disposition", "Hold");		
	}

}
