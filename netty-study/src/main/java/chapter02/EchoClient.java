package chapter02;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by Administrator on 2014/12/25.
 */
public class EchoClient {
    private final String host;
    private final int port;
    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //1. Create bootstrap for client
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group) //2. Specify EventLoopGroup to handle client events. NioEventLoopGroup is used, as the NIO-Transport should be used
                    .channel(NioSocketChannel.class) //3. Specify channel type; use correct one for NIO-Transport
                    .remoteAddress(new InetSocketAddress(host, port)) // 4.Set InetSocketAddress to which client connects
                    .handler(new ChannelInitializer<SocketChannel>() { //5. Specify ChannelHandler, using ChannelInitializer, called once connection established and channel created
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 6.Add EchoClientHandler to ChannelPipeline that belongs to channel. ChannelPipeline holds all ChannelHandlers of channel
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            //7. Connect client to remote peer; wait until sync() completes connect completes
            ChannelFuture future = bootstrap.connect().sync();
            //8. Wait until ClientChannel closes. This will block.
            future.channel().closeFuture().sync();
        } finally {
            //9. Shut down bootstrap and thread pools; release all resources
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String host = "localhost";
        int port = 1234;

        new EchoClient(host, port).start();
    }

}
