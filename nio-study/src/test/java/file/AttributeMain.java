package file;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.List;

/**
 * Created by Edgar on 2016/2/29.
 *
 * @author Edgar  Date 2016/2/29
 */
public class AttributeMain {
  public static void main(String[] args) throws IOException {
//    第三个并且是最后一个例子涉及了利用 java.nio.file.attribute 包中的类获取并设置文件属性的新的 API。
//
//    新的 API 能够提供对各种文件属性的访问。在以前的 Java 版本中，仅能得到基本的文件属性集（大小、修改时间、文件是否隐藏、以及它是文件还是目录）。为了获取或者修改更多的文件属性，必须利用运行所在平台特定的本地代码来实现，这很困难。很幸运的是，Java 7 能够允许您通过很简单的方式，利用 java.nio.file.attribute 类来读取，如果可能，修改扩展的属性集，完全去掉了这些操作的平台特定属性。
//
//    在新的 API 中有七个属性视图，其中一些特定于操作系统。这些 “ 视图 ” 类允许您获取并设置任何关联的属性，并且其中每个都具有对应的包含真实属性信息的属性类。让我们依次来看一下。
//    AclFileAttributeView 与 AclEntry
//
//    AclFileAttributeView 允许您为特定文件设置 ACL 及文件所有者属性。其 getAcl() 方法返回一个 List of AclEntry 对象，每个对应文件的一个权限集。其 setAcl(List<AclEntry>) 方法允许您修改该访问列表。这些属性视图仅可用于 Microsoft® Windows® 系统。
//    BasicFileAttributeView 与 BasicFileAttributes
//
//    这一视图类允许您获取一系列 —— 平常的 —— 基本文件属性，构建于以前的 Java 版本之上。其 readAttributes() 方法返回一个 BasicFileAttributes 实例，该实例包含最后修改时间、最后访问时间、创建时间、大小、以及文件属性等细节（常规文件、目录、符号链接、或者其他）。这一属性视图在所有平台上均可用。
    File attribFile = new File(".");
    Path attribPath = attribFile.toPath();
//    为获取想要的文件属性视图，我们在 Path 上使用 getFileAttributeView(Class viewClass) 方法。为获取 BasicFileAttributeView for attribPath，我们简单地调用：

    BasicFileAttributeView basicFileAttributeView =Files.getFileAttributeView(attribPath, BasicFileAttributeView.class);
//    正如前面所描述的，为从 BasicFileAttributeView 获取 BasicFileAttributes，我们只要调用其 readAttributes() 方法：
    BasicFileAttributes basicAttribs = basicFileAttributeView.readAttributes();
//    那么这样就可以了，现在已经得到了您所想要的任何基本文件属性。对于 BasicFileAttributes，只有创建、最后修改、以及最后访问时间可被修改（因为改变文件大小或者类型没有意义）。为改变这些，我们可以使用 java.nio.file.attribute.FileTime 类来创建新的时间，然后在 BasicFileAttributeView 上调用 setTimes() 方法。例如，我们可以不断地更新文件的最后修改时间。
    FileTime newModTime
            = FileTime.fromMillis(basicAttribs.lastModifiedTime().toMillis() + 60000);
    basicFileAttributeView.setTimes(newModTime, null, null);
//    这两个 null 指出，我们不想改变这一文件的最后访问时间或者创建时间。如果以前面相同的方式再次检查基本属性，您会发现最后修改时间已被修改，但是创建时间和最后访问时间还保持原样。

//    DosFileAttributeView 与 DosFileAttributes
//
//    这一视图类允许您获取指定给 DOS 的属性。（您可能会猜想，这一视图仅用于 Windows 系统。）其 readAttributes() 方法返回一个 DosFileAttributes 实例，该实例包含有问题的文件是否为只读、隐藏、系统文件、以及存档文件等细节信息。这一视图还包含针对每个属性的 set*(boolean) 方法。
//    FileOwnerAttributeView 与 UserPrincipal
//
//    这一视图类允许您获取并设置特定文件的所有者。其 getOwner()方法返回一个 UserPrincipal（还处于 java.nio.file.attribute 包中），其又具有 getName() 方法，来返回包含所有者名字的 String。该视图还提供 setOwner(UserPrincipal) 方法用于变更文件所有者。该视图在所有平台上都可用。
//    FileStoreSpaceAttributeView 与 FileStoreSpaceAttributes
//
//    这一用很吸引人的方式命名的类，允许您获取有关特定文件存储的信息。其 readAttributes() 方法返回一个包含文件存储的整个空间、未分配空间、以及已使用空间细节的 FileStoreSpaceAttributes 实例。这一视图在所有平台上都可用。
//    PosixFileAttributeView 与 PosixFileAttributes
//
//    这一视图类，仅在 UNIX® 系统上可用，允许您获取并设置指定给 POSIX（Portable Operating System Interface）的属性。其 readAttributes() 方法返回一个包含有关这一文件的所有者、组所有者、以及这一文件许可（这些细节通常用 UNIX chmod 命令设置）的 PosixFileAttributes 实例。这一视图还提供 setOwner(UserPrincipal)、 setGroup(GroupPrincipal)、以及 setPermissions(Set<PosixFilePermission>) 来修改这些属性。
//    UserDefinedFileAttributeView 与 String
//
//    这一视图类，仅可用于 Windows，允许您获取并设置文件的扩展属性。 这些属性跟其他的不同，它们只是名称值对，并可按需对其进行设置。 如果想向文件增加一些隐藏的元数据，而不必修改文件内容，这就很有用了。 这一属性提供 list() 方法，来为相关的文件返回 List of String 扩展属性的名字。
//
//    有了其名字后，就要获取特定属性的内容，这一视图具有一个 size(String name) 方法来返回属性值的大小，以及一个 read(String name, ByteBuffer dest) 方法来将属性值读取到 ByteBuffer 中。这一视图还提供 write(String name, ByteBuffer source) 方法来创建或者修改属性，以及一个 delete(String name) 方法来完全移除现有的属性。
//
//    这可能是最有趣的新属性视图，因为它允许您利用任意 String 名字和 ByteBuffer 值向文件增加属性。这很对 —— 其值是个 ByteBuffer，因此您可以在这里存储任何二进制数据。

    UserDefinedFileAttributeView userView
            = Files.getFileAttributeView(attribPath, UserDefinedFileAttributeView.class);
//    为获取用户为这一文件定义的属性名，我们在视图上调用 list() 方法：
    List<String> attribList = userView.list();
//    一旦我们拥有了想得到相关值的特定属性名，就为该值分配一个大小合适的 ByteBuffer，然后调用视图的 read(String, ByteBuffer) 方法：
//
//    ByteBuffer attribValue = ByteBuffer.allocate(userView.size(attribName));
//    userView.read(attribName, attribValue);
//
//    attribValue 现在包含了为那一特定属性所存储的任何数据。 想设置自己的属性，只需创建 ByteBuffer 并按需填入数据，然后在视图上调用 write(String,
// ByteBuffer) 方法：
//
//    userView.write(attribName, attribValue);
  }
}
