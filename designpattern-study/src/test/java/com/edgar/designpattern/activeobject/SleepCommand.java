package com.edgar.designpattern.activeobject;

public class SleepCommand implements Command {

    private Command wakeupCommand = null;
    private ActiveObjectEngine engine = null;
    private long sleepTime = 0;
    private long startTime = 0;
    private boolean started = false;

    public SleepCommand(Command wakeupCommand, ActiveObjectEngine engine,
            long sleepTime) {
        super();
        this.wakeupCommand = wakeupCommand;
        this.engine = engine;
        this.sleepTime = sleepTime;
    }

    @Override
    public void execute() {
        long currentTime = System.currentTimeMillis();
        if (!started) {
            started = true;
            startTime = currentTime;
            engine.addCommand(this);
        } else if ((currentTime - startTime) < sleepTime) {
            engine.addCommand(this);
        } else {
            engine.addCommand(wakeupCommand);
        }
    }

}
