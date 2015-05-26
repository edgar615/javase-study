import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/5/26.
 */
public class FileUploadRunnable implements Runnable {
    private final BlockingQueue<FileObject> queue;

    public FileUploadRunnable(BlockingQueue<FileObject> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        FileObject src = null;
        try {
            FtpFileSystemConfigBuilder builder = FtpFileSystemConfigBuilder.getInstance();
            FileSystemOptions opts = new FileSystemOptions();
            builder.setPassiveMode(opts, true);
            builder.setUserDirIsRoot(opts, true);
            FileSystemManager fileSystemManager = VFS.getManager();

            while (true) {
                src = queue.take();
                FileObject destFile = fileSystemManager.resolveFile("ftp://10.4.14.14/temp/zyz/"+src.getName().getBaseName(), opts);
                System.out.println(src.getName());
                System.out.println(src.getName().getRootURI());
                System.out.println(src.getName().getDepth());
                destFile.copyFrom(src, Selectors.SELECT_SELF);
                System.out.println("complete one");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (FileSystemException e) {
            e.printStackTrace();
        }
    }
}
