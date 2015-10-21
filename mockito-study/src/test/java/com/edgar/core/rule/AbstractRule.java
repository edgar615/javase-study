package com.edgar.core.rule;

public abstract class AbstractRule extends AbstractComponent {

    private AbstractComponent positiveOutcomeStep;
    private AbstractComponent negativeOutcomeStep;

    public void execute(Object arg) throws Exception {
        boolean outcome = makeDecision(arg);
        if (outcome)
            positiveOutcomeStep.execute(arg);
        else
            negativeOutcomeStep.execute(arg);

    }

    protected abstract boolean makeDecision(Object arg) throws Exception;

    public AbstractComponent getPositiveOutcomeStep() {
        return positiveOutcomeStep;
    }

    public void setPositiveOutcomeStep(AbstractComponent positiveOutcomeStep) {
        this.positiveOutcomeStep = positiveOutcomeStep;
    }

    public AbstractComponent getNegativeOutcomeStep() {
        return negativeOutcomeStep;
    }

    public void setNegativeOutcomeStep(AbstractComponent negativeOutcomeStep) {
        this.negativeOutcomeStep = negativeOutcomeStep;
    }

}