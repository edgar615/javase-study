package time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by Administrator on 2014/12/31.
 */
//ByteToMessageDecoder is an implementation of ChannelHandler which makes it easy to deal with the fragmentation issue.
public class TimeDecoder extends ByteToMessageDecoder {
    // ByteToMessageDecoder calls the decode() method with an internally maintained cumulative buffer whenever new data is received.
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            //decode() can decide to add nothing to out where there is not enough data in the cumulative buffer. ByteToMessageDecoder will call decode() again when there is more data received.
            return;
        }

        //If decode() adds an object to out, it means the decoder decoded a message successfully. ByteToMessageDecoder will discard the read part of the cumulative buffer. Please remember that you don't need to decode multiple messages. ByteToMessageDecoder will keep calling the decode() method until it adds nothing to out.
        out.add(in.readBytes(4));
    }
}
