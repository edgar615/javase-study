package thread.start;

/**
 * Created by Administrator on 2014/11/25.
 */
public class PrimeGenerator implements Runnable {
    @Override
    public void run() {
        long number = 1l;
        while (true) {
            if (isPrime(number)) {
                System.out.printf("Number %d is Prime", number);
            }
            if (Thread.currentThread().isInterrupted()) {
                System.out.printf("The Prime Generator has been Interrupted");
                return;
            }
            number++;
        }
    }

    private boolean isPrime(long number) {
        if (number <= 2) {
            return true;
        }
        for (long i = 2; i < number; i++) {
            if ((number % i) == 0) {
                return false;
            }
        }
        return true;
    }
}
