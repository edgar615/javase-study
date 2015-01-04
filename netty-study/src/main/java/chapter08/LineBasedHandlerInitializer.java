package chapter08;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * Created by Administrator on 2014/12/31.
 */
//DelimiterBasedFameDecoder和LineBasedFrameDecoder
public class LineBasedHandlerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //Add LineBasedFrameDecoder which will extract the frames and forward to the next handler in the  pipeline
        ch.pipeline().addLast(new LineBasedFrameDecoder(65 * 1024))
                .addLast(new FrameHandler());//Add FrameHandler that will receive the frames
    }

    private static class FrameHandler extends ChannelHandlerAdapter {

    }
}
