### Case study: transport migration
#### Using I/O and NIO without Netty 

*Blocking networking without Netty*

<pre>
public class PlainOioServer {
    public void serve(int port) throws IOException {
        //1. Bind server to port
        final ServerSocket serverSocket = new ServerSocket(port);
        try {
            while (true) {
                //2. Accept connection
                final Socket client = serverSocket.accept();
                System.out.println("Accepted connection from " + client);
                //3. Create new thread to handle connection
                new Thread() {
                    @Override
                    public void run() {
                        OutputStream out;
                        try {
                            //4. Write message to connected client
                            out = client.getOutputStream();
                            out.write("Hi!\r\n".getBytes(Charset.forName("UTF-8")));
                            out.flush();
                            //5. Close connection once message written and flushed
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
</pre>

*Asynchronous networking without Netty*

<pre>
public class PlainNioServer {
    public void serve(int port) throws IOException {
        System.out.println("Listening for connections on port " + port);
        ServerSocketChannel serverSocketChannel;
        Selector selector;

        serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        InetSocketAddress address = new InetSocketAddress(port);
        //1. Bind server to port
        serverSocket.bind(address);
        serverSocketChannel.configureBlocking(false);
        //2. Open selector that handles channels
        selector = Selector.open();
        //3. Register erverSocket to selector and specify that it is interested in new accepted clients
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());

        while (true) {
            try {
                //4. Wait for new events that are ready for process. This will block until something happens
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            //5. Obtain all SelectionKey instances that received events
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                try {
                    //6. Check if event was because new client ready to get accepted
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        System.out.println("Accepted connection from " + client);
                        client.configureBlocking(false);
                        //7. Accept client and register it to selector
                        client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, msg.duplicate());
                    }
                    //8. Check if event was because socket is ready to write data
                    if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        //9. Write data to connected client. This may not write all the data if the network is saturated. If so it will pick up the not-written data and write it once the network is writable again.
                        while (buffer.hasRemaining()) {
                            if (client.write(buffer) == 0) {
                                break;
                            }
                        }
                        //10. Close connection
                        client.close();
                    }
                } catch (IOException e) {
                    key.cancel();
                    try {
                        key.channel().close();
                    } catch (IOException ex) {

                    }
                }
            }
        }
    }
}
</pre>

#### Using I/O and NIO with Netty 
*Blocking networking with Netty*

<pre>
public class NettyOioServer {
    public void serve(int port) throws IOException, InterruptedException {
        final ByteBuf buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi!\r\n", Charset.forName("UTF-8")));
        //1. Create ServerBootstrap to allow bootstrap to server instance
        ServerBootstrap bootstrap = new ServerBootstrap();
        //2. Use OioEventLoopGroup Ito allow blocking mode (Old-IO)
        EventLoopGroup group = new OioEventLoopGroup();
        try {
            bootstrap.group(group)
                    .channel(OioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() { //3. Specify ChannelInitializer that will be called for each accepted connection
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //4. Add ChannelHandler to intercept events and allow to react on them
                            ch.pipeline().addLast(new ChannelHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    //5. Write message to client and add ChannelFutureListener to close connection once message written
                                    ctx.write(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);
                                }
                            });
                        }
                    });
            //6. Bind server to accept connections
            ChannelFuture future = bootstrap.bind().sync();
            future.channel().closeFuture().sync();
        } finally {
            //7. Release all resources
            group.shutdownGracefully().sync();
        }

    }
}
</pre>

*Implementing asynchronous support*

<pre>
public class NettyNioServer {

    public void serve(int port) throws InterruptedException {
        final ByteBuf buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi!\r\n", Charset.forName("UTF-8")));
        EventLoopGroup group = new NioEventLoopGroup();
        //1. Create ServerBootstrap to allow bootstrap to server
        ServerBootstrap bootstrap = new ServerBootstrap();

        try {
            bootstrap.group(group)
                    .channel(NioServerSocketChannel.class) //2. Use NioEventLoopGroup forI nonblocking mode
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {//3. Specify ChannelInitializer called for each accepted connection
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //4. Add ChannelHandler to intercept events and allow to react on them
                            ch.pipeline().addLast(new ChannelHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    //5. Write message to client and add ChannelFutureListener to close connection once message written
                                    ctx.write(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);
                                }
                            });
                        }
                    });
            ChannelFuture future = bootstrap.bind().sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
</pre>

### Transport API

Channel

ChannelConfig  The ChannelConfig has the entire configuration settings stored for the channel and allows 
for  updating  them  on  the  fly.

ChannelPipeline The ChannelPipeline holds  all of  the ChannelHandler  instances  that  should be used for  the  inbound  and  outbound  data  that  is  passed  through  the  channel. 
                
ChannelHandler               
 
- Transforming data from one format to another. 
- Notifying you of exceptions. 
- Notifying you when a Channel becomes active or inactive. 
- Notifying you once a channel is registered/deregistered from an EventLoop.
- Notifying you about user-specific events. 

The ChannelPipeline  implements  the  Intercepting  Filter Pattern, which means you  can  chain different  ChannelHandlers  and  intercept  the  data  or  events  which  go  through  the ChannelPipeline.

*Most important channel methods *

<table>
    <tr>
        <td>方法名</td>
        <td>描述</td>
    </tr>
    <tr>
        <td>eventLoop()</td>
        <td>Returns the EventLoop that is assigned to the channel  </td>
    </tr>
    <tr>
        <td>pipeline()</td>
        <td>Returns the ChannelPipeline that is assigned to the channel </td>
    </tr>
    <tr>
        <td>isActive()</td>
        <td>Returns if the channel is active, which means it's connected to the remote peer </td>
    </tr>
    <tr>
        <td>localAddress()</td>
        <td>Returns the SocketAddress that is bound local</td>
    </tr>
    <tr>
        <td>remoteAddress()</td>
        <td>Returns the SocketAddress that is bound remote</td>
    </tr>
    <tr>
        <td>write()</td>
        <td>Writes data to the remote peer. This data is passed though the ChannelPipeline</td>
    </tr>
</table>

#### NIO  Nonblocking I/O 
The  idea  is  that  a  user  can  register  to  get  notified  once  a  channels  state  changes.  The 
possible changes are: 

- A new Channel was accepted and is ready. 
- A Channel connection was completed. 
- A Channel has data received that is ready to be read. 
- A Channel is able to send more data on the channel.  