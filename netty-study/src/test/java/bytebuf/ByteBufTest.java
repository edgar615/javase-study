package bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ByteBufTest {

    @Test
    public void test() {
        ByteBuf buf = Unpooled.buffer(10);
        if (buf.hasArray()) {
            byte[] array = buf.array();
            int offset = buf.arrayOffset() + buf.capacity();
            int length = buf.readableBytes();
        }

    }
}
