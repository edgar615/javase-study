package concurrencyinpractice.chapter07;

import concurrencyinpractice.annotation.GuardedBy;
import org.omg.CORBA.TIMEOUT;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2014/12/11.
 */
public class LogService2 {

    private PrintWriter writer;

    private final ExecutorService exec = Executors.newSingleThreadExecutor();

    public void start() {

    }

    public void stop() throws InterruptedException {
        try {
            exec.shutdown();
            exec.awaitTermination(3, TimeUnit.SECONDS);
        } finally {
            writer.close();
        }
    }

    public void log(String msg) throws InterruptedException {
        try {
            exec.execute(new WriteTask(msg));
        } catch (RejectedExecutionException e) {

        }
    }

    private class WriteTask implements Runnable {
        private String msg;

        private WriteTask(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            writer.println(msg);
        }
    }

}
