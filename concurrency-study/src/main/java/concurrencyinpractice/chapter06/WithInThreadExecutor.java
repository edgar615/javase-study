package concurrencyinpractice.chapter06;

import java.util.concurrent.Executor;

/**
 * Created by Administrator on 2014/12/9.
 */
public class WithInThreadExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
