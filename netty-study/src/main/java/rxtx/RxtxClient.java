package rxtx;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.rxtx.RxtxChannel;
import io.netty.channel.rxtx.RxtxDeviceAddress;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Created by Administrator on 2015/1/20.
 */
public class RxtxClient {
    static final String PORT = System.getProperty("port", "/dev/ttyUSB0");

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new OioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(RxtxChannel.class)
                    .handler(new ChannelInitializer<RxtxChannel>() {
                        @Override
                        public void initChannel(RxtxChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new LineBasedFrameDecoder(32768),
                                    new StringEncoder(),
                                    new StringDecoder(),
                                    new RxtxClientHandler()
                            );
                        }
                    });

            ChannelFuture f = b.connect(new RxtxDeviceAddress(PORT)).sync();

            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
