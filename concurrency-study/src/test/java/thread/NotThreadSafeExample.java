package thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2014/11/24.
 */
public class NotThreadSafeExample {

    public static void main(String[] args) throws InterruptedException {
        NotThreadSafe sharedInstance = new NotThreadSafe();

        new Thread(new MyRunnable(sharedInstance)).start();
        new Thread(new MyRunnable(sharedInstance)).start();
        TimeUnit.SECONDS.sleep(5);
        System.out.println(sharedInstance.builder);
    }

    public static class MyRunnable implements Runnable{
        NotThreadSafe instance = null;

        public MyRunnable(NotThreadSafe instance){
            this.instance = instance;
        }

        public void run(){
            this.instance.add("some text");
        }
    }
}
