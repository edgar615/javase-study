ChannelPipeline维护了一组ChannelHandler的链表关系。
ChannelPipeline实现了拦截器模式，让用户可以在ChannelPipeline中完全控制一个事件及如何处理ChannelHandler与ChannelPipeline的交互。

每个通道在创建时，都会自动创建一个新的的ChannelPipeline并附加在通道上。

ChannelInitializer被注册到ServerBootstrap上，当通道注册后，会调用initChannel方法将ChannelHandler加入ChannelPipeline，任何从ChannelPipeline中删除自己

<pre>
initChannel((C) ctx.channel());
pipeline.remove(this);
</pre>

*ChannelPipeline处理I/O事件*

                                                     I/O Request
                                                via Channel or ChannelHandlerContext
                                                          |
      +---------------------------------------------------+---------------+
      |                           ChannelPipeline         |               |
      |                                                  \|/              |
      |    +----------------------------------------------+----------+    |
      |    |                   ChannelHandler  N                     |    |
      |    +----------+-----------------------------------+----------+    |
      |              /|\                                  |               |
      |               |                                  \|/              |
      |    +----------+-----------------------------------+----------+    |
      |    |                   ChannelHandler N-1                    |    |
      |    +----------+-----------------------------------+----------+    |
      |              /|\                                  .               |
      |               .                                   .               |
      | ChannelHandlerContext.fireIN_EVT() ChannelHandlerContext.OUT_EVT()|
      |          [method call]                      [method call]         |
      |               .                                   .               |
      |               .                                  \|/              |
      |    +----------+-----------------------------------+----------+    |
      |    |                   ChannelHandler  2                     |    |
      |    +----------+-----------------------------------+----------+    |
      |              /|\                                  |               |
      |               |                                  \|/              |
      |    +----------+-----------------------------------+----------+    |
      |    |                   ChannelHandler  1                     |    |
      |    +----------+-----------------------------------+----------+    |
      |              /|\                                  |               |
      +---------------+-----------------------------------+---------------+
                      |                                  \|/
      +---------------+-----------------------------------+---------------+
      |               |                                   |               |
      |       [ Socket.read() ]                    [ Socket.write() ]     |
      |                                                                   |
      |  Netty Internal I/O Threads (Transport Implementation)            |
      +-------------------------------------------------------------------+
      
inbound事件通常是由I/O线程触发，ChannelHandler可以监听到通道状态的变化，比如：连接的建立和关闭、从远程的客户端读取数据

outbound事件通常由outbound的I/O请求触发，比如：发起一个请求并尝试连接

#### 事件转发
ChannelHandler可以调用 ChannelHandlerContext中的事件的传播方法将事件传递给下一个ChannelHandler 


    Inbound event propagation methods:
        ChannelHandlerContext.fireChannelRegistered()
        ChannelHandlerContext.fireChannelActive()
        ChannelHandlerContext.fireChannelRead(Object)
        ChannelHandlerContext.fireChannelReadComplete()
        ChannelHandlerContext.fireExceptionCaught(Throwable)
        ChannelHandlerContext.fireUserEventTriggered(Object)
        ChannelHandlerContext.fireChannelWritabilityChanged()
        ChannelHandlerContext.fireChannelInactive()
    Outbound event propagation methods:
        ChannelHandlerContext.bind(SocketAddress, ChannelPromise)
        ChannelHandlerContext.connect(SocketAddress, SocketAddress, ChannelPromise)
        ChannelHandlerContext.write(Object, ChannelPromise)
        ChannelHandlerContext.flush()
        ChannelHandlerContext.read()
        ChannelHandlerContext.disconnect(ChannelPromise)
        ChannelHandlerContext.close(ChannelPromise)

*ChannelPipeline中维护ChannelHandler*

<pre>
ChannelPipeline ch = null;
FirstHandler firstHandler = new FirstHandler();
ch.addLast("handler1", firstHandler);
ch.addFirst("handler2", new SecondHandler());
ch.addLast("handler3", new ThirdHandler());

ch.remove("handler3");
ch.remove(firstHandler);
ch.replace("handler2", "handler4", new ForthHandler());
</pre>

### ChannelPipeline中inbound有关的方法

#### fireChannelRegistered()
通道注册了EventLoop，它会导致ChannelPipeline中下一个ChannelHandler的channelRegistered(ChannelHandlerContext) 方法被调用。 

#### fireChannelActive()
通道处于活动状态，意味着通道已经连接，它会导致ChannelPipeline中下一个ChannelHandler的channelActive(ChannelHandlerContext)方法被调用。 

#### fireChannelInactive()
通道处于Inactive状态，意味着通道已经关闭，它会导致ChannelPipeline中下一个ChannelHandler的channelInactive(ChannelHandlerContext) 方法被调用。 

#### fireExceptionCaught(Throwable)
通道的一个inbound操作抛出了异常，它会导致ChannelPipeline中下一个ChannelHandler的exceptionCaught(ChannelHandlerContext, Throwable)方法被调用。 

#### fireUserEventTriggered(Object)
通道收到一个用户自定义的事件，它会导致ChannelPipeline中下一个ChannelHandler的userEventTriggered(ChannelHandlerContext, Object)方法被调用。 

#### fireChannelRead(Object)
通道收到一个报文，它会导致ChannelPipeline中下一个ChannelHandler的channelRead(ChannelHandlerContext, Object)方法被调用。 

#### fireChannelReadComplete()
它会触发下一个ChannelHandler的ChannelHandler.channelWritabilityChanged(ChannelHandlerContext) 事件 

#### fireChannelWritabilityChanged()
它会触发下一个ChannelHandler的ChannelHandler.channelWritabilityChanged(ChannelHandlerContext) 事件 

### ChannelPipeline中outbound有关的方法

#### bind(SocketAddress)
#### connect(SocketAddress)
#### disconnect()
#### close()
#### read()
Request to Read data from the Channel into the first inbound buffer, triggers an ChannelHandler.channelRead(ChannelHandlerContext, Object) event if data was read, and triggers a channelReadComplete event so the handler can decide to continue reading. 
If there's a pending read operation already, this method does nothing. 
它会导致ChannelPipeline中下一个ChannelHandler的read(ChannelHandlerContext) 方法被调用。 
#### write(Object)
Request to write a message via this ChannelPipeline. This method will not request to actual flush, so be sure to call flush() once you want to request to flush all pending data to the actual transport.
#### flush()
Request to flush all pending messages.
#### writeAndFlush()