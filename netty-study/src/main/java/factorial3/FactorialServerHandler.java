package factorial3;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.math.BigInteger;

/**
 * Created by Administrator on 2015/1/9.
 */
public class FactorialServerHandler extends SimpleChannelInboundHandler<BigInteger> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, BigInteger msg) throws Exception {
        BigInteger number = (BigInteger) msg;
        System.out.println(number);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.err.printf("inactive");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
