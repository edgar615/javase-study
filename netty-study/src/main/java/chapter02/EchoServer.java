package chapter02;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by Administrator on 2014/12/24.
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //Bootstraps the server
            ServerBootstrap bootstrap = new ServerBootstrap();

            //Specifies NIO transport, local socket address
            bootstrap.group(group).channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                            //Adds handler to channel pipeline
                            // you  specify  the  ChannelHandler  to  call  when  a  connection  is  accepted,  which creates a child channel
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //The ChannelPipeline holds all of the different ChannelHandlers of a channel
                        ch.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            // you bind  the server and  then wait until  the bind completes,  the call  to  the  sync() method will cause  this  to block until  the server  is bound.
            ChannelFuture future = bootstrap.bind().sync();
            System.out.println(EchoServer.class.getName() +
                    "started and listen on" + future.channel().localAddress());
            future.channel().closeFuture().sync();
        } finally {
            //shutdown the EventLoopGroup and release all resources
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        new EchoServer(1234).start();
    }
}
