package factorial1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.math.BigInteger;

/**
 * Created by Administrator on 2015/1/9.
 */
public class FactorialServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("read");
        ByteBuf buf = (ByteBuf) msg;
        int length = buf.readableBytes();
        byte[] array = new byte[length];
        buf.getBytes(0, array);
        BigInteger number = new BigInteger(array);
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
