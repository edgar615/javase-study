package file;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Edgar on 2016/2/29.
 *
 * @author Edgar  Date 2016/2/29
 */
public class FileVisitorMain {
//
//  设想一个场景，您想要递归地访问一个目录树，在该树中的每个文件及目录上停下来，并为每个查找到的条目调用您自己的回调方法。在以前的 Java 版本中，这可能是个很痛苦的过程，包括递归列出目录、检查其条目、并自己调用回调。在 Java 7 中，这些都在 FileVisitor 中有提供，使用起来非常简单。
//
//  第一步是实现您自己的 FileVisitor 类。这个类包含 file-visitor 引擎穿越文件系统时所调用的回调方法。FileVisitor 接口由 5 个方法组成，在此处以遍历其间被调用的典型顺序来列出（T 在此处代表 java.nio.file.Path 或者超类）：
//
//  在访问目录中的条目之前调用 FileVisitResult preVisitDirectory(T dir)。它返回一个 FileVisitResult 枚举值，来告诉文件访问程序 API 下一步做什么。
//  当目录由于某些原因无法访问时，调用 FileVisitResult preVisitDirectoryFailed(T dir, IOException exception)。在第二个参数中指出了导致访问失败的异常。
//  在当前目录中有文件被访问时，调用 FileVisitResult visitFile(T file, BasicFileAttributes attribs)。该文件的属性传递给第二个参数。（可在本文 文件属性 部分更深入了解文件属性。）
//  当访问文件失败时，调用 FileVisitResult visitFileFailed(T file, IOException exception)。第二个参数指明导致访问失败的异常。
//  完成对目录及其子目录的访问后，调用 FileVisitResult postVisitDirectory(T dir, IOException exception)。当目录访问成功时，异常参数为空，或者包含导致目录访问过早结束的异常。
//
//  为了节约开发人员的时间， NIO.2 提供了 FileVisitor 的实现接口：java.nio.file.SimpleFileVisitor。该类以基础方式获取：对于 *Failed() 方法，它只是重新引发该异常，并且对于其他方法，它会继续下去而根本不做任何事！它的作用在于，您可以使用匿名类来替代您所希望替代的方法；剩下的方法会按默认方式实现。
  public static void main(String[] args) throws IOException {

    FileVisitor<Path> myFileVisitor = new SimpleFileVisitor<Path>() {

      @Override
      public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attribs) {
        System.out.println("I'm about to visit the "+dir+" directory"+" which has size " +attribs.size());
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attribs) {

        System.out.println("I'm visiting file "+file+" which has size " +attribs.size());
        return FileVisitResult.CONTINUE;
      }

    };
//    接下来，我们想创建开始我们文件访问的 Path。利用类 java.nio.Paths 完成这一操作：

    Path headDir = Paths.get(".");
//    我们可以利用两个方法中的任何一个在 类上启动树遍历：
//    public static void walkFileTree(Path head, FileVisitor<? super Path> fileVisitor) 浏览头目录下的文件树，在这一过程中调用在 fileVisitor 中实现的回调方法。
//    public static void walkFileTree(Path head, Set<FileVisitOption> options, int depth, FileVisitor<? super Path> fileVisitor) 与前面的方法相似，但是它给出两个附加的参数来指定访问选项，以及遍历将访问文件树中的多少个目录。
    Files.walkFileTree(headDir, myFileVisitor);
  }
}
