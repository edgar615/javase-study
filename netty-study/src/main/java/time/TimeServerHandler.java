package time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Administrator on 2014/12/30.
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接到服务器...");
        final ByteBuf buf = ctx.alloc().buffer(4);
        int time = (int) (System.currentTimeMillis() / 1000L + 2208988800L);
        buf.writeInt(time);
        final ChannelFuture ch = ctx.writeAndFlush(buf);
        System.out.println("服务器想客户端传输数据...");
        ch.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                assert ch == future;
                ctx.close();
                System.out.println("传输完成");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
