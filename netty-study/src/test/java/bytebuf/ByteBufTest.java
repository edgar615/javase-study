package bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteOrder;

/**
 * Created by Administrator on 2015/1/6.
 */
public class ByteBufTest {

    @Test
    public void testHeapBuf() {
        ByteBuf heapBuf = Unpooled.buffer(10);
        for (int i = 1; i < 15; i++) {
            heapBuf.writeByte(i);
        }
        if (heapBuf.hasArray()) {
            byte[] array = heapBuf.array();
            System.out.println(heapBuf.capacity());
            int offset = heapBuf.arrayOffset() + heapBuf.capacity();
            int length = heapBuf.readableBytes();
            System.out.println(length);
        }

    }

    @Test
    public void testDirectBuf() {
        ByteBuf directBuffer = Unpooled.directBuffer(10);
        for (int i = 1; i < 15; i++) {
            directBuffer.writeByte(i);
        }
        if (!directBuffer.hasArray()) {
            int length = directBuffer.readableBytes();
            byte[] array = new byte[length];
            directBuffer.getBytes(0, array);
            for (int j = 0; j < length; j++) {
                System.out.print(array[j] + " ");
            }
        }
    }

    @Test
    public void testCompositeBuf() {
        CompositeByteBuf compositeBuf = Unpooled.compositeBuffer();
        ByteBuf header = Unpooled.directBuffer(4);
        ByteBuf body = Unpooled.directBuffer();
        compositeBuf.writeInt(1234);
        compositeBuf.writeInt(12345);
    }

    @Test
    public void testGetByIndex() {
        ByteBuf buf = Unpooled.directBuffer();
        for (int i = 0; i < 10; i++) {
            buf.writeByte((byte) i);
        }
        for (int i = 0; i < buf.capacity(); i++) {
            byte b = buf.getByte(i);
            System.out.println(b);
        }
    }

    @Test
    public void testReadByIndex() {
        ByteBuf buf = Unpooled.directBuffer();
        for (int i = 0; i < 10; i++) {
            buf.writeByte((byte) i);
        }
        int length = buf.readableBytes();
        for (int i = 0; i < length; i++) {
            System.out.println(buf.readByte());
        }
    }

    @Test
    public void testDiscardBytes() {
        ByteBuf buf = Unpooled.directBuffer();
        for (int i = 0; i < 10; i++) {
            buf.writeByte((byte) i);
        }
        buf.readByte();
        buf.readByte();

        System.out.println("capacity：" + buf.capacity());
        System.out.println("readerIndex：" + buf.readerIndex());
        System.out.println("writerIndex：" + buf.writerIndex());
        System.out.println("readableBytes：" + buf.readableBytes());

        buf.discardReadBytes();
        System.out.println("capacity：" + buf.capacity());
        System.out.println("readerIndex：" + buf.readerIndex());
        System.out.println("writerIndex：" + buf.writerIndex());
        System.out.println("readableBytes：" + buf.readableBytes());
    }

    @Test
    public void testReadWrite() {
        ByteBuf buf = Unpooled.directBuffer(10);
        int i = 1;
        while (buf.isWritable()) {
            buf.writeByte((byte) i);
            i++;
        }
        while (buf.isReadable()) {
            System.out.println(buf.readByte());
        }
    }

    @Test
    public void testWrite() {
        ByteBuf buf = Unpooled.directBuffer(10);
        int i = 1;
        while (buf.writableBytes() >= 4) {
            buf.writeInt(i);
            i++;
        }
        while (buf.readableBytes() >= 4) {
            System.out.println(buf.readInt());
        }
    }

    @Test
    public void testMarkReset() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(1);
        buf.markWriterIndex();

        buf.writeLong(2);
        buf.readInt();
        buf.markReaderIndex();

        System.out.println("readerIndex：" + buf.readerIndex());
        System.out.println("writerIndex：" + buf.writerIndex());

        buf.readLong();
        System.out.println("readerIndex：" + buf.readerIndex());
        System.out.println("writerIndex：" + buf.writerIndex());

        buf.resetReaderIndex();
        buf.resetWriterIndex();
        System.out.println("readerIndex：" + buf.readerIndex());
        System.out.println("writerIndex：" + buf.writerIndex());

        buf.readerIndex(2);
        buf.writerIndex(2);
        System.out.println("readerIndex：" + buf.readerIndex());
        System.out.println("writerIndex：" + buf.writerIndex());
    }

    @Test
    public void testDuplicate() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 10; i++) {
            buf.writeByte((byte) i);
        }
        System.out.println("buf readerIndex：" + buf.readerIndex());
        System.out.println("buf writerIndex：" + buf.writerIndex());

        ByteBuf duplicated = buf.duplicate();
        System.out.println("duplicated readerIndex：" + duplicated.readerIndex());
        System.out.println("duplicated writerIndex：" + duplicated.writerIndex());

        buf.readByte();
        buf.writeInt(1);
        System.out.println("buf readerIndex：" + buf.readerIndex());
        System.out.println("buf writerIndex：" + buf.writerIndex());
        System.out.println("duplicated readerIndex：" + duplicated.readerIndex());
        System.out.println("duplicated writerIndex：" + duplicated.writerIndex());

        duplicated.readByte();
        duplicated.writeInt(1);
        System.out.println("buf readerIndex：" + buf.readerIndex());
        System.out.println("buf writerIndex：" + buf.writerIndex());
        System.out.println("duplicated readerIndex：" + duplicated.readerIndex());
        System.out.println("duplicated writerIndex：" + duplicated.writerIndex());
    }

    @Test
    public void testDuplicate2() {
        ByteBuf buf = Unpooled.buffer(10);
        for (int i = 0; i < 9; i++) {
            buf.writeByte((byte) i);
        }
        System.out.println("buf readerIndex：" + buf.readerIndex());
        System.out.println("buf writerIndex：" + buf.writerIndex());
        ByteBuf duplicated = buf.duplicate();
        System.out.println("duplicated readerIndex：" + duplicated.readerIndex());
        System.out.println("duplicated writerIndex：" + duplicated.writerIndex());

        if (buf.hasArray()) {
            byte[] array = buf.array();
            for (int i = 0; i < array.length; i++) {
                System.out.print(array[i] + " ");
            }
        }
        System.out.println();

        buf.writeByte(9);
        System.out.println("buf readerIndex：" + buf.readerIndex());
        System.out.println("buf writerIndex：" + buf.writerIndex());
        System.out.println("duplicated readerIndex：" + duplicated.readerIndex());
        System.out.println("duplicated writerIndex：" + duplicated.writerIndex());

        if (duplicated.hasArray()) {
            byte[] array = duplicated.array();
            for (int i = 0; i < array.length; i++) {
                System.out.print(array[i] + " ");
            }
        }
    }

    @Test
    public void testSlice() {
        ByteBuf buf = Unpooled.copiedBuffer("Hello world!", CharsetUtil.UTF_8);
        ByteBuf sliced1 = buf.slice();
        ByteBuf sliced2 = buf.slice(6, 12);

        Assert.assertTrue(buf.isWritable());
        Assert.assertFalse(sliced1.isWritable());
        Assert.assertFalse(sliced2.isWritable());

        while (buf.isReadable()) {
            System.out.print((char) buf.readByte());
        }
        System.out.println();
        while (sliced1.isReadable()) {
            System.out.print((char) sliced1.readByte());
        }
        System.out.println();
        while (sliced2.isReadable()) {
            System.out.print((char) sliced2.readByte());
        }
        System.out.println();

        buf.writeByte((char) '?');
        Assert.assertTrue(buf.isReadable());
        Assert.assertFalse(sliced1.isReadable());
        Assert.assertFalse(sliced2.isReadable());
    }

    @Test
    public void testOrder() {
        ByteBuf buf = Unpooled.buffer();
        ByteBuf bigOrder = buf.order(ByteOrder.BIG_ENDIAN);
        ByteBuf littleOrder = buf.order(ByteOrder.LITTLE_ENDIAN);

        buf.writeInt(1);

        Assert.assertEquals(buf.writerIndex(), bigOrder.writerIndex());
        Assert.assertEquals(buf.writerIndex(), littleOrder.writerIndex());

        System.out.println(buf.getInt(0));
        System.out.println(bigOrder.getInt(0));
        System.out.println(littleOrder.getInt(0));

        Assert.assertTrue(buf.isReadable());
        Assert.assertTrue(bigOrder.isReadable());
        Assert.assertTrue(littleOrder.isReadable());

        bigOrder.readInt();

        Assert.assertEquals(buf.readerIndex(), bigOrder.readerIndex());
        Assert.assertEquals(buf.readerIndex(), littleOrder.readerIndex());

        Assert.assertFalse(buf.isReadable());
        Assert.assertFalse(bigOrder.isReadable());
        Assert.assertFalse(littleOrder.isReadable());

    }

    @Test
    public void testCopy() {
        ByteBuf buf = Unpooled.copiedBuffer("Hello world!", CharsetUtil.UTF_8);
        ByteBuf copied = buf.copy();

        buf.setByte(0, (char) 'h');

        Assert.assertEquals('h', buf.readByte());
        Assert.assertEquals('H', copied.readByte());
    }

    @Test
    public void testGetSet() {
        ByteBuf buf = Unpooled.copiedBuffer("Hello world!", CharsetUtil.UTF_8);

        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();

        buf.getByte(1);
        buf.setByte(0, (char) 'h');

        Assert.assertEquals(readerIndex, buf.readerIndex());
        Assert.assertEquals(writerIndex, buf.writerIndex());
    }

    @Test
    public void testReadWrite1() {
        ByteBuf buf = Unpooled.copiedBuffer("Hello world!", CharsetUtil.UTF_8);
        System.out.println(buf.maxCapacity());
        System.out.println(buf.capacity());
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();

        buf.readByte();
        buf.writeByte((char) 'h');

        Assert.assertNotEquals(readerIndex, buf.readerIndex());
        Assert.assertNotEquals(writerIndex, buf.writerIndex());
    }

    @Test
    public void testCapacity() {
        ByteBuf buf = Unpooled.buffer();
        System.out.println(buf.maxCapacity());
        System.out.println(buf.capacity());
    }
}
