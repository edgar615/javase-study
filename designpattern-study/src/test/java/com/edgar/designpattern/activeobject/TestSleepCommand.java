package com.edgar.designpattern.activeobject;

import org.junit.Test;

public class TestSleepCommand {

    private boolean commendExecuted = false;
    
    @Test
    public void testSleep() throws Exception {
        Command wakeup = new Command() {
            
            @Override
            public void execute() throws Exception {
                commendExecuted = true;
            }
        };
        ActiveObjectEngine engine = new ActiveObjectEngine();
        SleepCommand sleepCommand = new SleepCommand(wakeup, engine, 1000);
        engine.addCommand(sleepCommand);
        long start = System.currentTimeMillis();
        engine.run();
        long stop = System.currentTimeMillis();
        long sleepTime = stop - start;
        System.out.println(sleepTime);
//        assertTrue(sleepTime > 1000);
//        assertTrue(sleepTime < 1100);
    }
}
