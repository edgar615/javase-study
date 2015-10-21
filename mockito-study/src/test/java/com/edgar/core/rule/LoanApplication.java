package com.edgar.core.rule;

import java.util.Arrays;

public class LoanApplication {
    public static final String INVALID_STATE = "Sorry we are not doing business in your state";
    public static final String INVALID_INCOME_EXPENSE_RATIO = "Sorry we cannot provide the loan given this expense/income ratio";
    public static final String APPROVED = "Your application has been approved";
    public static final String INSUFFICIENT_DATA = "You did not provide enough information on your application";
    public static final String INPROGRESS = "in progress";
    public static final String[] STATUSES = new String[]{INSUFFICIENT_DATA,
            INVALID_INCOME_EXPENSE_RATIO, INVALID_STATE, APPROVED, INPROGRESS};

    private String firstName;
    private String lastName;
    private double income;
    private double expences;
    private String stateCode;
    private String status;

    public void setStatus(String status) {
        if (!Arrays.asList(STATUSES).contains(status))
            throw new IllegalArgumentException("invalid status:" + status);
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getExpences() {
        return expences;
    }

    public void setExpences(double expences) {
        this.expences = expences;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStatus() {
        return status;
    }

}