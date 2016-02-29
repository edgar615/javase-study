package file;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 * Created by Edgar on 2016/2/29.
 *
 * @author Edgar  Date 2016/2/29
 */
public class WatchServiceMain {
  //  这一例子的场景很简单：您想要追踪特定目录（或多个目录）中是否有文件或者目录正被创建、修改、或者删除。您可能要利用这一信息来更新 GUI
  // 显示中列示的文件，或者想检查对将要重新加载的配置文件的修改。在以前的 Java
  // 版本中，必须实现一个代理，该代理运行在单独的线程中，来保持对目录所有内容的追踪，不断轮询文件系统来查看是否有相关的情况发生。在 Java 7 中，WatchService API
  // 提供了查看目录的能力。这就免除了自己编写文件系统轮询程序的所有麻烦，并且，如果可能的话，它可基于本地系统 API 来获取更优的性能。
  public static void main(String[] args) throws IOException, InterruptedException {
    //  第一步是通过 java.nio.file.FileSystems 类创建 WatchService 实例。本文不涉及文件系统的细节，因此在大多数情况下，您
// 会希望得到默认的文件系统，然后调用其 newWatchService() 方法：
    WatchService watchService = FileSystems.getDefault().newWatchService();

    //也可以使用Paths
    File watchDirFile = new File(".");
    Path watchDirPath = watchDirFile.toPath();

//    Path 类实现 java.nio.file.Watchable 接口，并且该接口定义我们将在这里例子中使用的 register() 方法。WatchKey register(WatchService watchService, WatchEvent.Kind<?>... events) 通过为所给特定事件所指定的 watchService 来注册这一方法所要调用的 Path。仅当在注册调用中指定了事件时，事件才会触发一个通知。
//
//    对于默认的 WatchService 实现，java.nio.file.StandardWatchEventKind 类定义三个 java.nio.file.StandardWatchEventKind 的静态实现，这些可用于 register() 调用：
//
//    StandardWatchEventKinds.ENTRY_CREATE 指出在所注册的 Path 中创建了文件或者目录。当文件重命名或者移入这一目录时，还触发了
// ENTRY_CREATE 事件。
//    StandardWatchEventKinds.ENTRY_MODIFY 指出在所注册的 Path
// 中文件或者目录被修改。究竟是哪些事件组成了修改，在一定程度上是平台特定的，但是在这里只想说，其实对文件内容的修改总会触发一个修改事件。在一些平台中，变更文件的属性也会触发这一事件。
//    StandardWatchEventKinds.ENTRY_DELETE 指出在所注册的 Path 中删除了文件或者目录。当对文件重命名或者将文件移出目录时，也会触发
// ENTRY_DELETE 事件。
    WatchKey watchKey = watchDirPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                                  StandardWatchEventKinds.ENTRY_MODIFY);
//    Path 现在注册为被监视，并且 WatchService 将总会一直在后台安静地工作，专心地监视目录。利用前面所展示的相同 Path 创建和 register() 调用，同一个 WatchService 实例能够监视多个目录。
//
//    现在已注册了 Path，我们可以用很方便的方式来检查 WatchService，看是否发生了任何我们感兴趣的事件。WatchService 提供三个方法来检查是否有任何令人激动的事发生。
//
//    如果有相关事件出现，WatchKey poll() 会返回下一个 WatchKey，或者没有注册的事件发生，会返回 null。
//    WatchKey poll(long timeout, TimeUnit unit) 需要超时和时间单元（java.util.concurrent.TimeUnit）。如果在特定时间范围内，有任何事件发生，这一方法存在，会返回相应的 WatchKey。如果在超时时间结束时，没有 WatchKeys 返回，这一方法将会返回 null。
//    WatchKey take() 与前面的方法相似，不同之处是，它将无限期等待，直到可以返回 WatchKey。
//
//    一旦这三个方法之一返回了 WatchKey，它将不会再被 poll() 或者 take() 调用返回，直到其 reset() 方法被调用。一旦 WatchService 返回了 WatchKey，就可以检查由于调用了 WatchKey 的 pollEvents() 方法而触发的事件，其将返回一列 WatchEvent。

    // Create a file inside our watched directory
    File tempFile = new File(watchDirFile, "tempFile");
    tempFile.createNewFile();

// Now call take() and see if the event has been registered
    WatchKey watchKey2 = watchService.take();
    for (WatchEvent<?> event : watchKey2.pollEvents()) {
      System.out.println(
              "An event was found after file creation of kind " + event.kind()
              + ". The event occurred on file " + event.context() + ".");
    }
  }
}
