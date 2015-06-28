package com.edgar.designpattern.activeobject;

public class DelayedTyper implements Command {

    private long itsDelay;
    private char itsChar;
    private static ActiveObjectEngine engine = new ActiveObjectEngine();
    private static boolean stop = false;
    
    public DelayedTyper(long itsDelay, char itsChar) {
        super();
        this.itsDelay = itsDelay;
        this.itsChar = itsChar;
    }

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        engine.addCommand(new DelayedTyper(100, '1'));
        engine.addCommand(new DelayedTyper(300, '3'));
        engine.addCommand(new DelayedTyper(500, '5'));
        engine.addCommand(new DelayedTyper(700, '7'));
        
        Command stopCommand = new Command() {
            
            @Override
            public void execute() throws Exception {
                stop = true;
            }
        };
        
        engine.addCommand(new SleepCommand(stopCommand, engine, 20000));
        engine.run();
    }

    @Override
    public void execute() throws Exception {
        System.out.print(itsChar);
        if (!stop) {
            delayAndRepeat();
        }
    }

    private void delayAndRepeat() {
        engine.addCommand(new SleepCommand(this, engine, itsDelay));
    }

}
