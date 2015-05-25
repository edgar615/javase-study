import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.junit.Test;

/**
 * Created by Administrator on 2015/5/25.
 */
public class FileObjectTest {

    @Test
    public void testFileObject() throws FileSystemException {
        FileSystemManager fileSystemManager = VFS.getManager();
        //Each file is represented by a FileObject instance. Using this interface you can create or delete the file, list its children, read or write its content, and so on
        FileObject jarFile = fileSystemManager.resolveFile("jar:f://commons-vfs2-examples-2.0-sources.jar");

        FileObject[] children = jarFile.getChildren();
        System.out.println("Children of " + jarFile.getName().getURI());
        for (FileObject fo : children) {
            System.out.println(fo.getName().getBaseName());
        }
    }
}
