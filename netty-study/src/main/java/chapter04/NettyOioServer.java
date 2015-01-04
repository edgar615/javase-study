package chapter04;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2014/12/26.
 */
public class NettyOioServer {
    public void serve(int port) throws IOException, InterruptedException {
        final ByteBuf buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi!\r\n", Charset.forName("UTF-8")));
        //1. Create ServerBootstrap to allow bootstrap to server instance
        ServerBootstrap bootstrap = new ServerBootstrap();
        //2. Use OioEventLoopGroup Ito allow blocking mode (Old-IO)
        EventLoopGroup group = new OioEventLoopGroup();
        try {
            bootstrap.group(group)
                    .channel(OioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() { //3. Specify ChannelInitializer that will be called for each accepted connection
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //4. Add ChannelHandler to intercept events and allow to react on them
                            ch.pipeline().addLast(new ChannelHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    //5. Write message to client and add ChannelFutureListener to close connection once message written
                                    ctx.write(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);
                                }
                            });
                        }
                    });
            //6. Bind server to accept connections
            ChannelFuture future = bootstrap.bind().sync();
            future.channel().closeFuture().sync();
        } finally {
            //7. Release all resources
            group.shutdownGracefully().sync();
        }

    }
}
