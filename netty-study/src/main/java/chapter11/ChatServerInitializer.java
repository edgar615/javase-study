package chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ChatServerInitializer extends ChannelInitializer<Channel> {

    private ChannelGroup group;

    public ChatServerInitializer(ChannelGroup group) {
        this.group = group;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //Decode  bytes  to  HTTP  requests  /  encode  HTTP requests to bytes.
        pipeline.addLast(new HttpServerCodec());
        //Allows to write a file content.
        pipeline.addLast(new ChunkedWriteHandler());
        //Aggregate decoded HttpRequest / HttpContent / LastHttpContent to FullHttpRequest. This way you will always receive only full Http requests
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
        //Handle FullHttpRequest which are not send to /ws URI and so serve the index.html page
        pipeline.addLast(new HttpRequestHandler("/ws"));
        //Handle the WebSocket upgrade and Ping/Pong/Close WebSocket frames to be RFC compliant
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        //Handles Text frames and handshake completion events
        pipeline.addLast(new TextWebSocketFrameHandler(group));
    }
}
