import org.apache.commons.vfs2.*;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Administrator on 2015/5/26.
 */
public class FileFinderRunnable implements Runnable {

    private final BlockingQueue<FileObject> queue;

    public FileFinderRunnable(BlockingQueue<FileObject> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            FileSystemManager fileSystemManager = VFS.getManager();

            FileObject srcFolder = fileSystemManager.resolveFile("file://f:/camelinaction-master");
            if (!FileType.FOLDER.equals(srcFolder.getType())) {
                throw new IllegalArgumentException("源路径必须是文件夹");
            }
            findFile(srcFolder);
        } catch (FileSystemException e) {
            throw new RuntimeException("FileSystemException");
        } catch (InterruptedException e) {
           throw new RuntimeException("failed put queue", e.getCause());
        }
    }

    public void findFile(FileObject srcFolder) throws FileSystemException, InterruptedException {
        FileObject[] children = srcFolder.getChildren();
        for (FileObject fo : children) {
            if (FileType.FILE.equals(fo.getType())) {
//                queue.put(fo);

            } else if (FileType.FOLDER.equals(fo.getType())) {
                findFile(fo);
            } else {
                System.out.println("unkown type");
            }
        }
    }
}
