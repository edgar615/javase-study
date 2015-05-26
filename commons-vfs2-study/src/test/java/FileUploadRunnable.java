import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/5/26.
 */
public class FileUploadRunnable implements Runnable {
    private final BlockingQueue<Command> queue;

    public FileUploadRunnable(BlockingQueue<Command> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Command command = queue.take();
                command.execute();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
