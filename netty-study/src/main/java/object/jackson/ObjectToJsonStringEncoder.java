package object.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Created by Administrator on 2015/2/5.
 */
public class ObjectToJsonStringEncoder extends MessageToMessageEncoder<Object> {
    private ObjectMapper mapper = new ObjectMapper();
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
        out.add(mapper.writeValueAsString(msg));
    }
}
