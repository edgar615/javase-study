package chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Administrator on 2015/1/5.
 */
public class FixedLengthFrameDecoderTest {

    @Test
    public void testFramesDecoded() {
        //1. Create a new ByteBuf and fill it with bytes
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i ++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();

        //2. Create a new EmbeddedByteChannel and feed in the FixedLengthFrameDecoder to test it
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));

        //write
        //4. Write bytes to it and check if they produced a new frame (message)
        Assert.assertTrue(channel.writeInbound(input.readBytes(9)));
//        Assert.assertTrue(channel.writeInbound(input));
        //5. Mark the channel finished
        Assert.assertTrue(channel.finish());

        //read
        //6. Read the produced messages and test if its what was expected
        Assert.assertEquals(buf.readBytes(3), channel.readInbound());
        Assert.assertEquals(buf.readBytes(3), channel.readInbound());
        Assert.assertEquals(buf.readBytes(3), channel.readInbound());
        Assert.assertNull(channel.readInbound());
    }

    @Test
    public void testFramesDecoded2() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i ++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));

        Assert.assertFalse(channel.writeInbound(input.readBytes(2)));
        Assert.assertTrue(channel.writeInbound(input.readBytes(7)));
        Assert.assertTrue(channel.finish());

        Assert.assertEquals(buf.readBytes(3), channel.readInbound());
        Assert.assertEquals(buf.readBytes(3), channel.readInbound());
        Assert.assertEquals(buf.readBytes(3), channel.readInbound());
        Assert.assertNull(channel.readInbound());
    }
}
