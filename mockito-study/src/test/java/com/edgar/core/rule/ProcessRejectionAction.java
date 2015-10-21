package com.edgar.core.rule;

public class ProcessRejectionAction extends AbstractPersistenceAwareAction {
    protected void doExecute(Object arg) throws Exception {
        LoanApplication application = (LoanApplication) arg;
        if (LoanApplication.INSUFFICIENT_DATA.equals(application.getStatus()))
            this.getPersistenceService().recordIncomplete(application);
        else
            this.getPersistenceService().recordRejection(application);
    }
}