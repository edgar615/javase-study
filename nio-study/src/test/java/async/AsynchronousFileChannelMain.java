package async;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Edgar on 2016/2/29.
 *http://www.ibm.com/developerworks/cn/java/j-nio2-1/index.html
 * @author Edgar  Date 2016/2/29
 */
public class AsynchronousFileChannelMain {
  public static void main(String[] args) throws IOException, InterruptedException {
//    AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(Paths.get("myfile"));
    //Paths.get(String) 实用方法，从代表文件名的 String 中创建 Path。
//    默认情况下，该文件已打开以供读取。open() 方法可利用附加选项来指定如何打开该文件。例如，此调用打开文件以供读取或写入，如果必要将创建该文件，并在通道关闭或者 JVM 终止时尝试删除文件：
    AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(Paths.get("afile"),
                                               StandardOpenOption.READ, StandardOpenOption.WRITE,
                                               StandardOpenOption.CREATE, StandardOpenOption.DELETE_ON_CLOSE);

    CompletionHandler<Integer, Object> handler =
            new CompletionHandler<Integer, Object>() {
              @Override
              public void completed(Integer result, Object attachment) {
                System.out.println(attachment + " completed with " + result + " bytes written");
              }
              @Override
              public void failed(Throwable e, Object attachment) {
                System.err.println(attachment + " failed with:");
                e.printStackTrace();
              }
            };

//    write() 方法采取：
//
//    包含要写入内容的 ByteBuffer
//    文件中的绝对位置
//            要传递给完成处理程序方法的附件对象
//    完成处理程序
//
//    操作必须给出进行读或写的文件中的绝对位置。文件具有内部位置标记，来指出读/写发生的位置，这样做没有意义，因为在上一个操作完成之前，就可以启动新操作，它们的发生顺序无法得到保证。由于相同的原因，在 AsynchronousFileChannel API 中没有用于设置或查询位置的方法，在 FileChannel 中同样也没有。
//
//    除了读写方法之外，还支持异步锁定方法，因此，如果当前有其他线程保持锁定时，可对文件进行执行访问锁定，而不必在当前线程中锁定（或者利用 tryLock 轮询）。
    byte[] bytes = new byte[1024];
    fileChannel.write(ByteBuffer.wrap(bytes), 0, "Write operation 1", handler);
    TimeUnit.SECONDS.sleep(5);

//    除了读写方法之外，还支持异步锁定方法，因此，如果当前有其他线程保持锁定时，可对文件进行执行访问锁定，而不必在当前线程中锁定（或者利用 tryLock 轮询）。


//    异步通道组
//
//    每个异步通道都属于一个通道组，它们共享一个 Java 线程池，该线程池用于完成启动的异步 I/O 操作。这看上去有点像欺骗，因为您可在自己的 Java 线程中执行大多数异步功能，来获得相同的表现，并且，您可能希望能够仅仅利用操作系统的异步 I/O 能力，来执行 NIO.2 ，从而获得更优的性能。然而，在有些情况下，有必要使用 Java 线程：比如，保证 completion-handler 方法在来自线程池的线程上执行。
//
//    默认情况下，具有 open() 方法的通道属于一个全局通道组，可利用如下系统变量对其进行配置：
//
//    java.nio.channels.DefaultThreadPoolthreadFactory，其不采用默认设置，而是定义一个 java.util.concurrent.ThreadFactory
//    java.nio.channels.DefaultThreadPool.initialSize，指定线程池的初始规模
//
//    java.nio.channels.AsynchronousChannelGroup 中的三个实用方法提供了创建新通道组的方法：
//
//    withCachedThreadPool()
//    withFixedThreadPool()
//    withThreadPool()

//    创建了具有线程池的新的通道组，该线程池包含 10 个线程，其中每个都构造为来自 Executors 类的线程工厂：

    AsynchronousChannelGroup tenThreadGroup =
            AsynchronousChannelGroup.withFixedThreadPool(10, Executors.defaultThreadFactory());

//    三个异步网络通道都具有 open() 方法的替代版本，它们采用给出的通道组而不是默认通道组。例如，当有异步操作请求时，此调用告诉 channel 使用 tenThreadGroup 而不是默认通道组来获取线程：

    AsynchronousServerSocketChannel channel =
            AsynchronousServerSocketChannel.open(tenThreadGroup);

    //定义自己的通道组可更好地控制服务于操作的线程，并能提供关闭线程或者等待终止的机制。清单 3 展示了相关的例子：
//    // first initiate a call that won't be satisfied
//    channel.accept(null, completionHandler);
//// once the operation has been set off, the channel group can
//// be used to control the shutdown
//    if (!tenThreadGroup.isShutdown()) {
//      // once the group is shut down no more channels can be created with it
//      tenThreadGroup.shutdown();
//    }
//    if (!tenThreadGroup.isTerminated()) {
//      // forcibly shutdown, the channel will be closed and the accept will abort
//      tenThreadGroup.shutdownNow();
//    }
//// the group should be able to terminate now, wait for a maximum of 10 seconds
//    tenThreadGroup.awaitTermination(10, TimeUnit.SECONDS);
  }
}
