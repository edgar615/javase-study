package chapter08;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2014/12/31.
 */
public class IdleStateHandlerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new IdleStateHandler(0, 0, 60, TimeUnit.SECONDS))//Add IdleStateHandler which will fire an IdleStateEvent if the connection has not received or send data for 60 seconds
                .addLast(new HeartbeatHandler());//The heartbeat to send to the remote peer
    }

    public static final class HeartbeatHandler extends ChannelHandlerAdapter {

        private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(
                Unpooled.copiedBuffer("HEARTBEAT", CharsetUtil.UTF_8)
        );

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                //Send the heartbeat and close the connection if the send operation fails
                ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate())
                        .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            } else {
                //Not of type IdleStateEvent pass it to the next handler in the ChannelPipeline
                super.userEventTriggered(ctx, evt);
            }
        }
    }
}
