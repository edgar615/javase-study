package com.edgar.core.rule;

import java.util.List;

public class ValidStateRule extends AbstractRule {

    private List<String> validStates;

    protected boolean makeDecision(Object arg) throws Exception {
        LoanApplication application = (LoanApplication) arg;
        if (validStates.contains(application.getStateCode())) {
            return true;
        }
        application.setStatus(LoanApplication.INVALID_STATE);
        return false;
    }

    public void setValidStates(List<String> validStates) {
        this.validStates = validStates;
    }
}