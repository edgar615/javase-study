package async;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Edgar on 2016/2/29.
 *http://www.ibm.com/developerworks/cn/java/j-nio2-1/index.html
 * @author Edgar  Date 2016/2/29
 */
public class AsynchronousServerSocketMain {

  public static void main(String[] args) throws IOException, ExecutionException,
          InterruptedException, TimeoutException {
    //打开 AsychronousServerSocketChannel 并将其绑定到类似于 ServerSocketChannel 的地址
    //方法 bind() 将一个套接字地址作为其参数。找到空闲端口的便利方法是传递一个 null 地址，它会自动将套接字绑定到本地主机地址，并使用空闲的 临时 端口。
    AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open().bind(null);
    //告诉通道接受一个连接
    Future<AsynchronousSocketChannel> future = server.accept();

//    利用 Future 对象，当前线程可阻塞来等待结果：
    AsynchronousSocketChannel worker = future.get();
    //超时
    //AsynchronousSocketChannel worker = future.get(10, TimeUnit.SECONDS);
    //取消
//    if (!future.isDone()) {
//      future.cancel(true);//cancel() 方法可利用一个布尔标志来指出执行接受的线程是否可被中断
//    }
//
//// read a message from the client
    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    worker.read(readBuffer).get(10, TimeUnit.SECONDS);
    System.out.println("Message: " + new String(readBuffer.array()));
  }
}
