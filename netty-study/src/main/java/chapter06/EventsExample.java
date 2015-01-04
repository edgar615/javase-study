package chapter06;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

/**
 * Created by Administrator on 2014/12/29.
 */
public class EventsExample {

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
}
