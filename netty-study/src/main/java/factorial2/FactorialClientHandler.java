package factorial2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.math.BigInteger;

public class FactorialClientHandler extends ChannelHandlerAdapter {
    private static final int COUNT = 1000;
    private int next = 1;
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        BigInteger number = new BigInteger("26595070873586078267675089443919345642534678891615111717255884146861997520511666");
        ctx.writeAndFlush(number);
    }
}
