package com.edgar.designpattern.payroll.schedules;

import com.edgar.designpattern.payroll.payrolldomain.PaymentSchedule;

public class WeeklySchedule implements PaymentSchedule {

	@Override
	public boolean isPayDate(String payDate) {
		return payDate.endsWith("07") || payDate.endsWith("14")
				|| payDate.endsWith("21") || payDate.endsWith("28");
	}

}
