package object.user;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Administrator on 2015/1/20.
 */
public class ObjectEchoServerHandler extends SimpleChannelInboundHandler<User> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, User msg) throws Exception {
        System.out.println(msg.getUsername());
    }
}
