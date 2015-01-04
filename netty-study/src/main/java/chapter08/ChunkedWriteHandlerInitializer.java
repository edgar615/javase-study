package chapter08;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.stream.ChunkedStream;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by Administrator on 2014/12/31.
 */
public class ChunkedWriteHandlerInitializer extends ChannelInitializer<SocketChannel> {
    private final File file;

    public ChunkedWriteHandlerInitializer(File file) {
        this.file = file;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new ChunkedWriteHandler())//Add ChunkedWriteHandler to handle ChunkedInput implementations
                .addLast(new WriteStreamHandler());//Add WriteStreamHandler to write a ChunkedInput
    }

    public final class WriteStreamHandler extends ChannelHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            //Write the content of the file via a ChunkedStream once the connection is established (we use a FileInputStream only for demo purposes, any InputStream works)
            ctx.writeAndFlush(new ChunkedStream(new FileInputStream(file)));
        }
    }
}
