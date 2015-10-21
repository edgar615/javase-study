package com.edgar.core.rule;

public abstract class AbstractPersistenceAwareAction extends AbstractAction {
    private LoanApplicationPersistenceInterface persistenceService;

    public void setPersistenceService(
            LoanApplicationPersistenceInterface persistenceService) {
        this.persistenceService = persistenceService;
    }

    public LoanApplicationPersistenceInterface getPersistenceService() {
        return persistenceService;
    }
}