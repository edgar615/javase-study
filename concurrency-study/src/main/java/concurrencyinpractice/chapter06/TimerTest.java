package concurrencyinpractice.chapter06;

import java.util.Timer;
import java.util.TimerTask;
 
public class TimerTest {
    private Timer timer = new Timer();
 
    // 启动计时器
    public void lanuchTimer() {
        timer.schedule(new TimerTask() {
            public void run() {
                throw new RuntimeException();
            }
        }, 1000 * 3, 500);
    }
 
    // 向计时器添加一个任务
    public void addOneTask() {
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("hello world");
            }
        }, 1000 * 1, 1000 * 5);
    }
 
    public static void main(String[] args) throws Exception {
        TimerTest test = new TimerTest();
        test.lanuchTimer();
        Thread.sleep(1000 * 5);// 5秒钟之后添加一个新任务
        test.addOneTask();
    }
}