package com.edgar.core.rule;

public abstract class AbstractAction extends AbstractComponent {

    private AbstractComponent nextStep;

    public void execute(Object arg) throws Exception {
        this.doExecute(arg);
        if (nextStep != null)
            nextStep.execute(arg);
    }

    protected abstract void doExecute(Object arg) throws Exception;

    public void setNextStep(AbstractComponent nextStep) {
        this.nextStep = nextStep;
    }

    public AbstractComponent getNextStep() {
        return nextStep;
    }

}