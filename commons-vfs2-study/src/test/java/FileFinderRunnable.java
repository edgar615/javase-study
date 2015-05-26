import org.apache.commons.lang3.StringUtils;
import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Administrator on 2015/5/26.
 */
public class FileFinderRunnable implements Runnable {

    private final BlockingQueue<Command> queue;
    private FileSystemManager fileSystemManager;
    private String srcPath;

    public FileFinderRunnable(BlockingQueue<Command> queue) {
        this.queue = queue;
        try {
            this.fileSystemManager = VFS.getManager();
        } catch (FileSystemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try {
            FileObject srcFolder = fileSystemManager.resolveFile("file:////f:/camelinaction-master");
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
        int srcDepth = srcFolder.getName().getDepth();
        for (FileObject fo : children) {
            if (FileType.FILE.equals(fo.getType())) {
                int relativeDepth = fo.getName().getDepth() - srcDepth;
                StringBuilder destFilePath = new StringBuilder("ftp://10.4.14.14/temp/zyz/");
                System.out.println(fo.getName().getFriendlyURI());
                String relativePath = StringUtils.substring(fo.getName().getURI(), "file:////f:/camelinaction-master".length() -1);
                System.out.println(relativePath);
                FileObject destFile = fileSystemManager.resolveFile("ftp://10.4.14.14/temp/zyz/" + fo.getName().getBaseName());
                Command command = new FileUploadCommand(fo, destFile);
                queue.put(command);
            } else if (FileType.FOLDER.equals(fo.getType())) {
                findFile(fo);
            } else {
                System.out.println("unkown type");
            }
        }
    }
}
