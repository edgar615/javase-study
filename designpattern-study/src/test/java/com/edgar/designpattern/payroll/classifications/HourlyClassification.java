package com.edgar.designpattern.payroll.classifications;

import com.edgar.designpattern.payroll.payrolldomain.Paycheck;
import com.edgar.designpattern.payroll.payrolldomain.PaymentClassification;

import java.util.HashMap;
import java.util.Map;

public class HourlyClassification implements PaymentClassification {
	private double hourlyRate;

	private final Map<String, TimeCard> timeCardMap = new HashMap<String, TimeCard>();

	public HourlyClassification(double hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	public TimeCard getTimeCard(String date) {
		return timeCardMap.get(date);
	}

	public void addTimeCard(TimeCard timeCard) {
		timeCardMap.put(timeCard.getDate(), timeCard);
	}

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Map<String, TimeCard> getTimeCardMap() {
        return timeCardMap;
    }

    @Override
	public double calculatePay(Paycheck pc) {
		double sum = 0.0;
		for (TimeCard timeCard : timeCardMap.values()) {
			double hours = timeCard.getHours();
			if (hours <= 8) {
				sum += timeCard.getHours() * hourlyRate;
			} else {
				sum += 8 * hourlyRate + (hours - 8) * 1.5 * hourlyRate;
			}
		}
		return sum;
	}

}
