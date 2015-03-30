package com.edgar.netty.channel;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;

/**
 * ChannelPipeline中的头结点
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
public class HeadHandler extends ChannelHandlerAdapter {

    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        //TODO unsafe.bind
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        //TODO unsafe.connect
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        //TODO unsafe.disconnect
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        //TODO unsafe.close
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        //TODO unsafe.read
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        //TODO unsafe.write
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        //TODO unsafe.flush
    }
}
