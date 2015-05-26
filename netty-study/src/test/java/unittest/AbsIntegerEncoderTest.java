package unittest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Administrator on 2015/1/5.
 */
public class AbsIntegerEncoderTest {

    @Test
    public void testEncoded() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 1; i < 10; i++) {
            buf.writeInt(i * -1);
        }

        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());
        Assert.assertTrue(channel.writeOutbound(buf));
        Assert.assertTrue(channel.finish());

//        ByteBuf output = channel.readOutbound();
//        for (int i = 1; i < 10; i ++) {
//            Assert.assertEquals(i, channel.readOutbound());
//        }
//        Assert.assertFalse(output.isReadable());
//        Assert.assertNull(channel.readOutbound());

        for (int i = 1; i < 10; i ++) {
            Assert.assertEquals(i, channel.readOutbound(), 0);
        }
        Assert.assertNull(channel.readOutbound());
    }
}
