每个ChannelHandler被添加到ChannelPipeline后，都会创建一个ChannelHandlerContext并与之创建的ChannelHandler关联绑定（一个ChannelHandler可以对应多个ChannelHandlerContext）。
ChannelHandlerContext允许ChannelHandler与其他的ChannelHandler实现进行交互，这是相同ChannelPipeline的一部分。
ChannelHandlerContext不会改变添加到其中的ChannelHandler，因此它是安全的。

#### 通知下一个ChannelHandler
ChannelHandlerContext可以通过调用各种不同的方法来通知在同一个ChannelPipeline中离它最近的ChannelHandler。
通知开始的地方取决你如何设置

如果你想有一些事件流全部通过ChannelPipeline，有两个不同的方法可以做到：

    调用Channel的方法
    调用ChannelPipeline的方法
    
如果是一个inbound事件，它开始于头部；若是一个outbound事件，则开始于尾部。

<pre>
public void eventsViaChannel(ChannelHandlerContext ctx) {
    Channel channel = ctx.channel();
    channel.write(Unpooled.copiedBuffer("Netty in Action", CharsetUtil.UTF_8));
}

public void eventsViaChannelPipeline(ChannelHandlerContext ctx) {
    ChannelPipeline ch = ctx.pipeline();
    ch.write(Unpooled.copiedBuffer("Netty in Action", CharsetUtil.UTF_8));
}

public void eventsViaChannelHandlerContext(ChannelHandlerContext ctx) {
    ctx.write(Unpooled.copiedBuffer("Netty in Action", CharsetUtil.UTF_8));
}
</pre>

#### channel的状态
channel有四个状态

channelActive

channelInactive

channelRegistered

### Bootstrap、ServerBootstrap
启动器

### Channel
通道

### ChannelHandler
通道事件处理的接口

### EventLoop
EventLoop用来处理通道的I/O事件，一个EventLoop可以处理多个通道的I/O事件。
EventLoopGroup使用迭代器来实现一组EventLoop

### ChannelFuture
Netty中的I/O事件是异步的。ChannelFuture可以注册ChannelFutureListener监听器，监听操作是否完成的通知。
