package chapter07;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.*;

import java.util.List;

/**
 * Created by Administrator on 2014/12/30.
 */
@ChannelHandler.Sharable
public class WebSocketConvertHandler extends MessageToMessageCodec<WebSocketFrame, WebSocketConvertHandler.WebSocketFrame2> {
    public static final WebSocketConvertHandler INSTANCE = new WebSocketConvertHandler();
    @Override
    protected void encode(ChannelHandlerContext ctx, WebSocketFrame2 msg, List<Object> out) throws Exception {
        switch (msg.getType()) {
            case  BINARY:
                out.add(new BinaryWebSocketFrame(msg.getData()));
                break;
            case CLOSE:
                out.add(new CloseWebSocketFrame(true, 0, msg.getData()));
                break;
            case PING:
                out.add(new PingWebSocketFrame(msg.getData()));
                break;
            case PONG:
                out.add(new PongWebSocketFrame(msg.getData()));
                break;
            case CONTINUATION:
                out.add(new ContinuationWebSocketFrame(msg.getData()));
                break;
            case TEXT:
                out.add(new TextWebSocketFrame(msg.getData()));
                break;
            default:
                throw new IllegalStateException("Unsupported websocket msg " + msg);
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, io.netty.handler.codec.http.websocketx.WebSocketFrame msg, List<Object> out) throws Exception {
        if (msg instanceof BinaryWebSocketFrame) {
            out.add(new WebSocketFrame2(WebSocketFrame2.FrameType.BINARY, msg.content().copy()));
            return;
        }
        if (msg instanceof CloseWebSocketFrame) {
            out.add(new WebSocketFrame2(WebSocketFrame2.FrameType.CLOSE, msg.content().copy()));
            return;
        }
        if (msg instanceof PingWebSocketFrame) {
            out.add(new WebSocketFrame2(WebSocketFrame2.FrameType.PING, msg.content().copy()));
            return;
        }
        if (msg instanceof PongWebSocketFrame) {
            out.add(new WebSocketFrame2(WebSocketFrame2.FrameType.PONG, msg.content().copy()));
            return;
        }
        if (msg instanceof TextWebSocketFrame) {
            out.add(new WebSocketFrame2(WebSocketFrame2.FrameType.TEXT, msg.content().copy()));
            return;
        }
        if (msg instanceof ContinuationWebSocketFrame) {
            out.add(new WebSocketFrame2(WebSocketFrame2.FrameType.CONTINUATION, msg.content().copy()));
            return;
        }
        throw new IllegalStateException("Unsupported websocket msg " + msg);
    }

    public static final class WebSocketFrame2 {
        public enum FrameType {
            BINARY,
            CLOSE,
            PING,
            PONG,
            TEXT,
            CONTINUATION
        }

        private final FrameType type;
        private final ByteBuf data;
        public WebSocketFrame2(FrameType type, ByteBuf data) {
            this.type = type;
            this.data = data;
        }

        public FrameType getType() {
            return type;
        }

        public ByteBuf getData() {
            return data;
        }
    }
}
