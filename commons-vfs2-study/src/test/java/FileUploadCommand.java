import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder;

/**
 * Created by Administrator on 2015/5/26.
 */
public class FileUploadCommand implements Command {
    private final FileObject src;
    private final FileObject dest;

    public FileUploadCommand(FileObject src, FileObject dest) {
        this.src = src;
        this.dest = dest;
    }

    @Override
    public void execute() {
        FtpFileSystemConfigBuilder builder = FtpFileSystemConfigBuilder.getInstance();
        FileSystemOptions opts = new FileSystemOptions();
        builder.setPassiveMode(opts, true);
        builder.setUserDirIsRoot(opts, true);
        try {
            FileSystemManager fileSystemManager = VFS.getManager();
            dest.copyFrom(src, Selectors.SELECT_SELF);
            System.out.println("upload " + src.getName().getBaseName());
        } catch (FileSystemException e) {
            e.printStackTrace();
        }
    }
}
