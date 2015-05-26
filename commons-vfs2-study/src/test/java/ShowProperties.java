import org.apache.commons.vfs2.*;

import java.text.DateFormat;
import java.util.Date;

public class ShowProperties
{
    public static void main(String[] args) throws FileSystemException {
        if (args.length == 0)
        {
            System.err.println("Please pass the name of a file as parameter.");
            System.err.println("e.g. java org.apache.commons.vfs2.example.ShowProperties LICENSE.txt");
            return;
        }
        for (int i = 0; i < args.length; i++)
        {
            try
            {
                FileSystemManager mgr = VFS.getManager();
                System.out.println();
                System.out.println("Parsing: " + args[i]);
                FileObject file = mgr.resolveFile(args[i]);
                System.out.println("URL: " + file.getURL());
                System.out.println("getName(): " + file.getName());
                System.out.println("BaseName: " + file.getName().getBaseName());
                System.out.println("Extension: " + file.getName().getExtension());
                System.out.println("Path: " + file.getName().getPath());
                System.out.println("Scheme: " + file.getName().getScheme());
                System.out.println("URI: " + file.getName().getURI());
                System.out.println("Root URI: " + file.getName().getRootURI());
                System.out.println("Parent: " + file.getName().getParent());
                System.out.println("Type: " + file.getType());
                System.out.println("Exists: " + file.exists());
                System.out.println("Readable: " + file.isReadable());
                System.out.println("Writeable: " + file.isWriteable());
                System.out.println("Root path: " + file.getFileSystem().getRoot().getName().getPath());
                if (file.exists())
                {
                    if (file.getType().equals(FileType.FILE))
                    {
                        System.out.println("Size: " + file.getContent().getSize() + " bytes");
                    }
                    else if (file.getType().equals(FileType.FOLDER) && file.isReadable())
                    {
                        FileObject[] children = file.getChildren();
                        System.out.println("Directory with " + children.length + " files");
                        for (int iterChildren = 0; iterChildren < children.length; iterChildren++)
                        {
                            System.out.println("#" + iterChildren + ": " + children[iterChildren].getName());
                            if (iterChildren > 5)
                            {
                                break;
                            }
                        }
                    }
                    System.out.println("Last modified: " + DateFormat.getInstance().format(new Date(file.getContent().getLastModifiedTime())));
                }
                else
                {
                    System.out.println("The file does not exist");
                }
                file.close();
            }
            catch (FileSystemException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}