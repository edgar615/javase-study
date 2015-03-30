package com.edgar.netty.channel;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * ChannelPipeline中的尾结点，对于Inbound Event除释放资源外，不做处理
 *
 * Inbound Event始于头部，Outbound Event始于尾部
 * Inbound Event:
 * <pre>
 *    fireChannelRegistered()
 *    fireChannelActive()
 *    fireChannelRead(Object)
 *    fireChannelReadComplete()
 *    fireExceptionCaught(Throwable)
 *    fireUserEventTriggered(Object)
 *    fireChannelWritabilityChanged()
 *    fireChannelInactive()
 * </pre>
 *
 * Outbound Event:
 * <pre>
 *       bind(SocketAddress, ChannelPromise)
 *       connect(SocketAddress, SocketAddress, ChannelPromise)
 *       write(Object, ChannelPromise)
 *       flush()
 *       read()
 *       disconnect(ChannelPromise)
 *       close(ChannelPromise)
 * </pre>
 */
public class TailHandler extends ChannelHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //TODO 打印异常日志
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            //TODO 打印日志
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
    }
}
