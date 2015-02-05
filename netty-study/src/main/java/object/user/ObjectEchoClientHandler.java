package object.user;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Random;

/**
 * Created by Administrator on 2015/1/20.
 */
public class ObjectEchoClientHandler extends ChannelHandlerAdapter {
    private final String[] names = new String[] {"Edgar", "Adonis", "Brera", "Leona"};

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 100; i ++)
        ctx.writeAndFlush(new User(names[new Random().nextInt(4)]));
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
