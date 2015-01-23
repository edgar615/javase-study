package chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.InetAddress;

/**
 * Created by Administrator on 2015/1/22.
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {
    static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        // Once session is secured, send a greeting and register the channel to the global channel
        // list so the channel received the messages from others.
        ctx.writeAndFlush(
                "Welcome to " + InetAddress.getLocalHost().getHostName() + " secure chat service!\n");
        channels.add(ctx.channel());
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
        // Send the received message to all channels but the current one.
        for (Channel channel : channels) {
            if (channel != ctx.channel()) {
                channel.writeAndFlush("[" + ctx.channel().remoteAddress() + "] " + msg + '\n');
            } else {
                channel.writeAndFlush("[you] " + msg + "\n");
            }
        }

        if ("bye".equals(msg.toLowerCase())) {
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
