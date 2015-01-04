package discard;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.awt.*;

/**
 * Created by Administrator on 2014/12/30.
 */
public class DiscardServer {
    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {
        // accepts an incoming connection
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //handles the traffic of the accepted connection once the boss accepts the connection and registers the accepted connection to the worker.
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //ServerBootstrap is a helper class that sets up a server
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)//use the NioServerSocketChannel class which is used to instantiate a new Channel to accept incoming connections.
                    .localAddress(port)
                    .childHandler(new ChannelInitializer<SocketChannel>() {//The handler specified here will always be evaluated by a newly accepted Channel.The ChannelInitializer is a special handler that is purposed to help a user configure a new Channel.
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)//option() is for the NioServerSocketChannel that accepts incoming connections.
                    .childOption(ChannelOption.SO_KEEPALIVE, true);//childOption() is for the Channels accepted by the parent ServerChannel,

            ChannelFuture cf = bootstrap.bind().sync();
            cf.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully().sync();
            bossGroup.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new DiscardServer(port).run();
    }
}
