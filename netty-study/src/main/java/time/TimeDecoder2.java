package time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Created by Administrator on 2014/12/31.
 */
//ByteToMessageDecoder is an implementation of ChannelHandler which makes it easy to deal with the fragmentation issue.
public class TimeDecoder2 extends ReplayingDecoder<Void> {
    // ByteToMessageDecoder calls the decode() method with an internally maintained cumulative buffer whenever new data is received.
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(in.readBytes(4));
    }
}
