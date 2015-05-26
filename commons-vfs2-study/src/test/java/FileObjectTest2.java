import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2015/5/26.
 */
public class FileObjectTest2 {
    @Test
    public void testFileObject() throws IOException, InterruptedException {
        BlockingQueue<FileObject> queue = new LinkedBlockingDeque<>(10);
        ExecutorService exec = Executors.newCachedThreadPool();
        //producer
        exec.submit(new FileFinderRunnable(queue));
        //consumer
        exec.submit(new FileUploadRunnable(queue));
        TimeUnit.MINUTES.sleep(1);
    }
}
