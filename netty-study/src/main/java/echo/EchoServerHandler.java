package echo;

import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Administrator on 2014/12/30.
 */
public class EchoServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server received: " + ByteBufUtil.hexDump((io.netty.buffer.ByteBuf) msg));
        ctx.write(msg);

        //A ChannelHandlerContext object provides various operations that enable you to trigger various I/O events and operations. Here, we invoke write(Object) to write the received message in verbatim. Please note that we did not release the received message unlike we did in the DISCARD example. It is because Netty releases it for you when it is written out to the wire.
        // ctx.write(Object) does not make the message written out to the wire. It is buffered internally, and then flushed out to the wire by ctx.flush(). Alternatively, you could call ctx.writeAndFlush(msg) for brevity.

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
