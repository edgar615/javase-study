package object.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Created by Administrator on 2015/2/5.
 */
public class JsonStringToObjectDecoder extends MessageToMessageDecoder<String> {
    private ObjectMapper mapper = new ObjectMapper();
    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        out.add(mapper.readValue(msg, User.class));
    }
}
