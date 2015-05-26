import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder;
import org.apache.commons.vfs2.provider.zip.ZipFileObject;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Administrator on 2015/5/25.
 */
public class FileObjectTest {

    @Test
    public void testFileObject() throws IOException {
        FtpFileSystemConfigBuilder builder = FtpFileSystemConfigBuilder.getInstance();
        FileSystemOptions opts = new FileSystemOptions();
        builder.setPassiveMode(opts, true);
        builder.setUserDirIsRoot(opts, true);
        FileSystemManager fileSystemManager = VFS.getManager();

        FileObject srcFolder = fileSystemManager.resolveFile("file://f:/camelinaction-master");
        if (!FileType.FOLDER.equals(srcFolder.getType())) {
            throw new IllegalArgumentException("源路径必须是文件夹");
        }
        FileObject destFile = fileSystemManager.resolveFile("ftp://10.4.14.14/temp/zyz/", opts);
        destFile.copyFrom(srcFolder, Selectors.EXCLUDE_SELF);
    }
}
