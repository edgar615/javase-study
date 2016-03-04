package async;

/**
 * Created by Edgar on 2016/2/29.
 *http://www.ibm.com/developerworks/cn/java/j-nio2-1/index.html
 * @author Edgar  Date 2016/2/29
 */
public class AsynchronousDatagramChannelMain {
  public static void main(String[] args) {
//    最后的新通道是 AsynchronousDatagramChannel。它与 AsynchronousSocketChannel 很类似，但由于 NIO.2 API 在该通道级别增加了对多播的支持，而在 NIO 中只在 MulticastDatagramSocket 级别才提供这一支持，因此有必要将其单独提出。Java 7 中的 java.nio.channels.DatagramChannel 也能提供这一功能。
//
//    作为服务器来使用的 AsynchronousDatagramChannel 可构建如下：
//
//    AsynchronousDatagramChannel server = AsynchronousDatagramChannel.open().bind(null);
//
//    接下来，可设置客户端来接收发往一个多播地址的数据报广播。首先，必须在多播地址范围内选择一个地址（从 224.0.0.0 到 239.255.255.255），还要选择一个所有客户端都可绑定的端口：
//
//// specify an arbitrary port and address in the range
//    int port = 5239;
//    InetAddress group = InetAddress.getByName("226.18.84.25");
//
//    我们也需要一个到所使用网络接口的引用：
//
//// find a NetworkInterface that supports multicasting
//    NetworkInterface networkInterface = NetworkInterface.getByName("eth0");
//
//    现在，打开数据报通道并设置多播选项，如清单 4 所示：
//    清单 4. 打开数据报通道并设置多播选项
//
//// the channel should be opened with the appropriate protocol family,
//// use the defined channel group or pass in null to use the default channel group
//    AsynchronousDatagramChannel client =
//            AsynchronousDatagramChannel.open(StandardProtocolFamily.INET,  tenThreadGroup);
//// enable binding multiple sockets to the same address
//    client.setOption(StandardSocketOption.SO_REUSEADDR, true);
//// bind to the port
//    client.bind(new InetSocketAddress(port));
//// set the interface for sending datagrams
//    client.setOption(StandardSocketOption.IP_MULTICAST_IF, networkInterface);
//
//    客户端可通过如下方式加入多播组：
//
//    MembershipKey key = client.join(group, networkInterface);
//
//    java.util.channels.MembershipKey 是提供对组成员控制的新类。利用该键，您可丢弃组成员、阻塞或者取消阻塞来自特定地址的数据报、以及返回有关组和通道的消息。
//
//    服务器可以向特定地址和端口发送数据报，供客户端接收，如清单 5 所示：
//    清单 5. 发送以及接收数据报
//
//// send message
//    ByteBuffer message = ByteBuffer.wrap("Hello to all listeners".getBytes());
//    server.send(message, new InetSocketAddress(group, port));
//
//// receive message
//    final ByteBuffer buffer = ByteBuffer.allocate(100);
//    client.receive(buffer, null, new CompletionHandler<SocketAddress, Object>() {
//      @Override
//      public void completed(SocketAddress address, Object attachment) {
//        System.out.println("Message from " + address + ": " +
//                           new String(buffer.array()));
//      }
//
//      @Override
//      public void failed(Throwable e, Object attachment) {
//        System.err.println("Error receiving datagram");
//        e.printStackTrace();
//      }
//    });
//
//    可在同一端口上创建多个客户端，它们可加入多播组来接收来自服务器的数据报。
  }
}
