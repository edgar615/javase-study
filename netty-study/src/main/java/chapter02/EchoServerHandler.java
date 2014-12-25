package chapter02;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;

/**
 * Created by Administrator on 2014/12/24.
 */
//Annotate with @Sharable to share between channels
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //Write the received messages back . Be aware that this will not flush the messages to the remote peer yet.
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Server received: " + ByteBufUtil.hexDump(in));
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //Flush all previous written messages (that are pending) to the remote peer, and close the channel after the operation is complete.
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //Log exception
        cause.printStackTrace();
        //Close channel
        ctx.close();
    }
}
