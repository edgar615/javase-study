package bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.junit.Test;

/**
 * Created by Administrator on 2015/1/8.
 */
public class ByteBufUtilTest {

    @Test
    public void testHex() {
        ByteBuf buf = Unpooled.copiedBuffer("Edgar", CharsetUtil.UTF_8);
        System.out.println(ByteBufUtil.hexDump(buf));
        System.out.println(ByteBufUtil.hexDump(buf, 0, 3));
        System.out.println(ByteBufUtil.swapInt(1));
        System.out.println(ByteBufUtil.swapLong(1));
    }
}
