package thread.start;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Administrator on 2014/11/25.
 */
public class Main8 {

    public static void main(String[] args) {
        Task task=new Task();
        Thread thread=new Thread(task);
        thread.setUncaughtExceptionHandler(new ExceptionHandler());
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Thread has finished\n");

    }
}
