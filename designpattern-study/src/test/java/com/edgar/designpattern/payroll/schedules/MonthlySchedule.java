package com.edgar.designpattern.payroll.schedules;

import com.edgar.designpattern.payroll.payrolldomain.PaymentSchedule;

public class MonthlySchedule implements PaymentSchedule {

	@Override
	public boolean isPayDate(String payDate) {
		return payDate.endsWith("30") || payDate.endsWith("31");
	}

}
