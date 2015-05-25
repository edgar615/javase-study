import org.apache.commons.vfs2.*;
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
        FileSystemManager fileSystemManager = VFS.getManager();
        //Each file is represented by a FileObject instance. Using this interface you can create or delete the file, list its children, read or write its content, and so on
        FileObject jarFile = fileSystemManager.resolveFile("file:///home/edgar/dev/test/TarenaBaiWenBaiDa.pdf");
//        System.out.println(jarFile.getClass());
//        System.out.println(jarFile.getType());
//        FileObject[] children = jarFile.getChildren();
//        System.out.println("Children of " + jarFile.getName().getURI());
//        for (FileObject fo : children) {
//            System.out.println(fo.getName().getBaseName());
//        }

        FileObject destFile = fileSystemManager.resolveFile("file:///home/edgar/dev/vfs/a");
        destFile.createFile();
//        ZipOutputStream zos = new ZipOutputStream(destFile.getContent().getOutputStream());
//        // add entry/-ies.
//        ZipEntry zipEntry = new ZipEntry("name_inside_zip");
//        InputStream is = jarFile.getContent().getInputStream();
//
//// Write to zip.
//        byte[] buf = new byte[1024];
//        zos.putNextEntry(zipEntry);
//        for (int readNum; (readNum = is.read(buf)) != -1;) {
//            zos.write(buf, 0, readNum);
//        }

//        destFile.createFolder();
//        System.out.println(destFile.getType());
//        if (destFile.exists()) {
//            System.out.println(destFile.getName().getURI());
//        } else {
//            destFile.createFile();
//            destFile.copyFrom(jarFile, Selectors.SELECT_SELF);
//        }
//        System.out.println(destFile.getType());
        destFile.copyFrom(jarFile, Selectors.SELECT_SELF);
//        jarFile.moveTo(destFile);
    }
}
