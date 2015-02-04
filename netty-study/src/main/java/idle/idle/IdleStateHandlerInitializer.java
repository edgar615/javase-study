package idle.idle;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/2/4.
 */
public class IdleStateHandlerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new IdleStateHandler(3, 5, 10, TimeUnit.SECONDS));
        pipeline.addLast(new HeartbeatHandler());

        pipeline.addLast(new ChannelHandlerAdapter() {
            @Override
            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                ctx.writeAndFlush(Unpooled.copiedBuffer("Hello", CharsetUtil.UTF_8));
            }
        });
    }
    public static final class HeartbeatHandler extends ChannelHandlerAdapter {

        private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(
                Unpooled.copiedBuffer("HEARTBEAT", CharsetUtil.UTF_8)
        );

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent e = (IdleStateEvent) evt;
                if (e.state() == IdleState.READER_IDLE) {
                    System.out.println("Reader idle, closing channel");
                    //e.getChannel().close();
                }
                else if (e.state() == IdleState.WRITER_IDLE) {
                    System.out.println("Writer idle, sending heartbeat");
                }
                else if (e.state() == IdleState.ALL_IDLE) {
                    System.out.println("All idle, sending heartbeat");
                }else if (e.state() == IdleState.ALL_IDLE) {
                    System.out.println("All idle, sending heartbeat");
                }
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
