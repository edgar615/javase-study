package pojo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

/**
 * Created by Administrator on 2014/12/31.
 */
public class UnixTimeEncoder extends ChannelHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        UnixTime m = (UnixTime) msg;
        ByteBuf buf = ctx.alloc().buffer(4);
        buf.writeLong(m.value());
        ctx.write(buf, promise);
    }
}
