package chapter08;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Created by Administrator on 2014/12/31.
 */
public class LengthBasedInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(65 * 1024, 0, 8))//Add LengthFieldBasedFrameDecoder to extract the frames based on the encoded length in the header
                .addLast(new FrameHandler());//Add FrameHandler to handle the frames
    }

    private static class FrameHandler extends ChannelHandlerAdapter {

    }
}
