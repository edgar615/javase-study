package thread.semaphore;

public class ReceivingThread {

    Semaphore semaphore = null;

    public ReceivingThread(Semaphore semaphore) {

        this.semaphore = semaphore;

    }

    public void run() {

        while (true) {

            try {
                this.semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//receive signal, then do something...

        }

    }

}