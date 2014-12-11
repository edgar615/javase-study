package concurrencyinpractice.chapter07;

/**
 * Created by Administrator on 2014/12/11.
 */
public class ShutdonwHook {

    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
//                try {
//                    LogService.this.stop();
//                } catch (InterruptedException e) {
//
//                }
            }
        });
    }
}
