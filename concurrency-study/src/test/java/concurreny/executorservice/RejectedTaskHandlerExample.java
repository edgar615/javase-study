package concurreny.executorservice;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Administrator on 2014/11/26.
 */
public class RejectedTaskHandlerExample {

    public static void main(String[] args) {
        ThreadPoolExecutor executorService= (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
        executorService.setRejectedExecutionHandler(new RejectedTaskController());
        for (int i = 0; i < 10; i ++) {
            executorService.execute(new Task(i+ ""));
        }
        //关闭
        executorService.shutdown();
        executorService.execute(new Task("11"));
    }
}
