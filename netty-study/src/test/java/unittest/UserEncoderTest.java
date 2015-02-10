package unittest;

import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import object.jackson.JsonStringToObjectDecoder;
import object.jackson.ObjectToJsonStringEncoder;
import object.jackson.User;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Administrator on 2015/2/9.
 */
public class UserEncoderTest {

    @Test
    public void testEncoder() {
        User user = new User("Edgar");
        EmbeddedChannel channel = new EmbeddedChannel(new ObjectToJsonStringEncoder(), new StringEncoder());
        Assert.assertTrue(channel.writeOutbound(user));
        Assert.assertTrue(channel.finish());
        Assert.assertEquals("{\"username\":\"Edgar\"}", channel.readOutbound());
    }

    @Test
    public void testDecoder() {
        EmbeddedChannel channel = new EmbeddedChannel(new StringDecoder(), new JsonStringToObjectDecoder());
        Assert.assertTrue(channel.writeInbound("{\"username\":\"Edgar\"}"));
        Assert.assertTrue(channel.finish());
        User user = channel.readInbound();
        Assert.assertEquals("Edgar", user.getUsername());
    }
}
