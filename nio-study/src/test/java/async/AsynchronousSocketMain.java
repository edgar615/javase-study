package async;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;

/**
 * Created by Edgar on 2016/2/29.
 *http://www.ibm.com/developerworks/cn/java/j-nio2-1/index.html
 * @author Edgar  Date 2016/2/29
 */
public class AsynchronousSocketMain {
  public static void main(String[] args) throws IOException, ExecutionException,
          InterruptedException {
    //一旦客户端与服务器建立连接，可通过使用字节缓存的通道来执行读写操作
    AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
//    client.connect(server.getLocalAddress()).get();

//    send a message to the server
    ByteBuffer message = ByteBuffer.wrap("ping".getBytes());
    client.write(message).get();

  }
}
