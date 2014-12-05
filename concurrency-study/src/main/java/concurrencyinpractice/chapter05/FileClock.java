package concurrencyinpractice.chapter05;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FileClock implements Runnable {

	@Override
    public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.printf("%s\n", new Date());
			try {
				// Sleep during one second
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
                System.out.println("after throw InterruptedException the interrupt flag : " + Thread.currentThread().isInterrupted());
				System.out.println("The FileClock has been interrupted");
			}
		}
	}
}