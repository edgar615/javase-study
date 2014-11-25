package thread.start;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2014/11/25.
 */
public class Main3 {

    public static void main(String[] args) throws IOException, InterruptedException {
        FileSearch searcher=new FileSearch("C:\\","autoexec.bat");
        Thread thread=new Thread(searcher);
        thread.start();

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }

}
