package thread.start;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Class that writes the actual date to a file every second
 * 
 */
public class FileClock implements Runnable {

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.printf("%s\n", new Date());
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Thread interrupted\n Exiting...");
                break;
            } else {
                try {
                    // Sleep during one second
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("before throw InterruptedException the interrupt flag : " + Thread.currentThread().isInterrupted());
                    System.out.println("The FileClock has been interrupted");
                    Thread.currentThread().interrupt();
                    System.out.println("after throw InterruptedException the interrupt flag : " + Thread.currentThread().isInterrupted());
                }
            }

		}
	}
}