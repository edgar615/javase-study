package file;

/**
 * Created by Edgar on 2016/2/29.
 *
 * @author Edgar  Date 2016/2/29
 */
public class FileMain {
//  Java 平台早就需要一个文件系统接口而不是 java.io.File 类。 该类不会在平台中以一贯的方式来处理文件名，它不支持高效文件属性访问，不允许复杂应用程序利用可用的文件系统特定特性（比如，符号链接）， 而且，其大多数方法在出错时仅返回失败，而不会提供异常信息。
//
//  补救措施是 Java 7 试用版中的三个新的文件系统包：
//
//  java.nio.file
//  java.nio.file.attribute
//  java.nio.file.spi
//
//  本文重点关注这些包中最有用的类：
//
//  java.nio.file.Files 与 java.nio.file.FileVisitor 使得您可以在文件系统中漫步，在特定目录深度查询文件或者目录，并可对每个查询结果执行用户实现的回调方法。
//  java.nio.file.Path 与 java.nio.file.WatchService 允许 “ 注册 ” 来监视特定目录。如果在目录中发生了文件创建、修改或者删除操作，监视目录的应用程序将收到通知。
//  java.nio.attribute.*AttributeView 允许查看此前对于 Java 用户隐藏的文件和目录属性。这些属性包括文件所有者及组权限，访问控制列表（ACL），以及扩展文件属性。
  public static void main(String[] args) {

  }
}
