package thread.start;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;

/**
 * Created by Administrator on 2014/11/25.
 */
public class Main7 {

    public static void main(String[] args) {

        Deque<Event> deque=new ArrayDeque<Event>();

        // Creates the three WriterTask and starts them
        WriterTask writer=new WriterTask(deque);
        for (int i=0; i<3; i++){
            Thread thread=new Thread(writer);
            thread.start();
        }

        // Creates a cleaner task and starts them
        CleanerTask cleaner=new CleanerTask(deque);
        Thread cleanerThread = new Thread(cleaner);
        cleanerThread.setDaemon(true);
        cleanerThread.start();
    }
}
