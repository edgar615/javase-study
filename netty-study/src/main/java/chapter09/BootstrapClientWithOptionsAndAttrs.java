package chapter09;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

/**
 * Created by Administrator on 2015/1/5.
 */
public class BootstrapClientWithOptionsAndAttrs {
    public void bootstrap() {
        //1. Create a new AttributeKey under which well store the attribute value
        final AttributeKey<Integer> id = AttributeKey.valueOf("ID");
        //2. Create a new bootstrap to create new client channels and connect them
        Bootstrap bootstrap = new Bootstrap();
        //3. Specify the EventLoopGroup to get EventLoops from and register with the channels
        bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class) //4. Specify the channel class that will be used to instance
                .handler(new ChannelHandlerAdapter() {
                    @Override
                    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                        // 6. Retrieve the attribute with the AttributeKey and its value
                        Integer idValue = ctx.channel().attr(id).get();
                        // do something  with the idValue
                    }

                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        System.out.println("Reveived data");
                        // do something
                    }
                });
        //7. Set the ChannelOptions that will be set on the created channels on connect or bind
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
        //8. Assign the attribute
        bootstrap.attr(id, 1234);
        //9. Connect to the remote host with the configured bootstrap
        ChannelFuture ch = bootstrap.connect("127.0.0.1", 8080);
        ch.syncUninterruptibly();
    }
}
