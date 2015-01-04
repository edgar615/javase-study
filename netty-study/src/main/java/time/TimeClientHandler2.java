package time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * Created by Administrator on 2014/12/31.
 */
public class TimeClientHandler2 extends ChannelHandlerAdapter {

    private ByteBuf buf;

    //A ChannelHandler has two life cycle listener methods: handlerAdded() and handlerRemoved(). You can perform an arbitrary (de)initialization task as long as it does not block for a long time.
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        buf = ctx.alloc().buffer(4);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        buf.release();
        buf = null;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf m = (ByteBuf) msg;
        // all received data should be cumulated into buf
        buf.writeBytes(m);
        m.release();

        //And then, the handler must check if buf has enough data, 4 bytes in this example, and proceed to the actual business logic. Otherwise, Netty will call the channelRead() method again when more data arrives, and eventually all 4 bytes will be cumulated.
        if (buf.readableBytes() >= 4) {
            long currentTimeMillis = (buf.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println(new Date(currentTimeMillis));
            ctx.close();
        }
    }
}
