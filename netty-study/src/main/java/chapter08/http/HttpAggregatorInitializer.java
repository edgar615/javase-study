package chapter08.http;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Created by Administrator on 2014/12/31.
 */
public class HttpAggregatorInitializer extends ChannelInitializer<SocketChannel> {
    private final boolean client;

    public HttpAggregatorInitializer(boolean client) {
        this.client = client;
    }


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (client) {
            //Add HttpClientCodec as we are in client mode
            pipeline.addLast("codec", new HttpClientCodec());
        } else {
            //Add HttpServerCodec as we are in server mode
            pipeline.addLast("codec", new HttpServerCodec());
        }
        //Add HttpObjectAggregator to the ChannelPipeline, using a max message size of 512kb. After the message is getting bigger a TooLongFrameException is thrown.
        pipeline.addLast("aggegator", new HttpObjectAggregator(512 * 1024));
    }
}
