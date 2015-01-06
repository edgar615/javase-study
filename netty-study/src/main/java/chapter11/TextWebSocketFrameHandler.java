package chapter11;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * Created by Administrator on 2015/1/6.
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private ChannelGroup group;

    public TextWebSocketFrameHandler(ChannelGroup group) {
        this.group = group;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //If the event is received that indicate that the handshake was successful remove the HttpRequestHandler from the ChannelPipeline as no further HTTP messages will be send.
        if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            ctx.pipeline().remove(HttpRequestHandler.class);
            //Write a message to all connected WebSocket clients about a new Channel that is now also connected
            group.writeAndFlush(new TextWebSocketFrame("Client " + ctx.channel() + " joined"));
            //Add the now connected WebSocket Channel to the ChannelGroup so it also receive all messages
            group.add(ctx.channel());
        } else {
            super.userEventTriggered(ctx,evt);
        }
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //Retain the received message and write and flush it to all connected WebSocket clients
        group.write(msg.retain());
    }
}
