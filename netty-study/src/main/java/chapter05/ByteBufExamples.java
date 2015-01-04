package chapter05;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * Created by Administrator on 2014/12/26.
 */
public class ByteBufExamples {

    public void accessBackArrayInHeapBuf() {
        ByteBuf buf = null; //...
        if (buf.hasArray()) {
            byte[] array = buf.array();
            int offset = buf.arrayOffset() + buf.capacity();
            int length = buf.readableBytes();
            //doSomething
        }
    }

    public void accessDataArrayInDirectBuf() {
        ByteBuf buf = null; //...
        if (!buf.hasArray()) {
            int length = buf.readableBytes();
            byte[] array = new byte[length];
            buf.getBytes(0, array);
            //doSomething
        }
    }

    public void compositeBuf() {
        ByteBuf heapBuf = null; //...
        ByteBuf directBuf = null;
        CompositeByteBuf compBuf = null;
        compBuf.addComponents(heapBuf, directBuf);

        compBuf.removeComponent(0);

    }

    public void accessDataArrayInCompositBuf() {
        CompositeByteBuf compBuf = null;
        if (!compBuf.hasArray()) {
            int length = compBuf.readableBytes();
            byte[] array = new byte[length];
            compBuf.getBytes(0, array);
            //doSomething
        }
    }

    public void accessData() {
        ByteBuf buf = null;
        for (int i = 0; i < buf.capacity(); i++) {
            //Be aware  that index pased access will not advance  the readerIndex or writerIndex. You can advance it by hand by call readerIndex(index) and writerIndex(index) if needed.
            byte b = buf.getByte(i);
            System.out.print((char) b);
        }
    }

    public void discard() {
        ByteBuf buf = null;
        for (int i = 0; i < buf.capacity(); i++) {
            //Be aware  that index pased access will not advance  the readerIndex or writerIndex. You can advance it by hand by call readerIndex(index) and writerIndex(index) if needed.
            byte b = buf.getByte(i);
            System.out.print((char) b);
        }
        buf.discardReadBytes();
    }

    public void readBytes() {
        ByteBuf buf = null;
        if (buf.isReadable()) {
            byte b = buf.readByte();
        }
    }

    public void writeBytes() {
        ByteBuf buf = null;
        if (buf.writableBytes() > 4) {
            buf.writeInt(1);
        }
    }

    public void clear() {
        ByteBuf buf = null;
        if (buf.writableBytes() > 4) {
            buf.writeInt(1);
        }
        buf.clear();//readIndex和writeIndex设为0，不会发生数据拷贝
    }

    public void search() {
        ByteBuf buf = null;
        buf.indexOf(0, 10, (byte) 1);
        //ByteBufProcessor
        buf.bytesBefore((byte) 1);
    }

    public void markReset() {
        ByteBuf buf = null;

        buf.markReaderIndex();
        buf.markWriterIndex();
        buf.resetReaderIndex();
        buf.resetWriterIndex();
        buf.readerIndex(10);
        buf.writerIndex(10);
    }

    public void createView() {
        //To  create  a  view  of  an  existing  buffer,  call  duplicate(),  slice(),  slice(int, int), readOnly(),  or  order(ByteOrder)
        //A  derived  buffer  has  an  independent  readerIndex,  writerIndex, and marker indexes,
        // but it shares other internal data representation the way a NIO ByteBuffer does.

        //If  a  fresh  copy  of  an  existing  buffer  is  required,  use  the  copy()  or  copy(int, int) method instead.
    }

    public static void main(String[] args) {
        new ByteBufExamples().readWrite();
    }

    public void slice() {
        Charset utf8 = Charset.forName("UTF-8");
        //1. Create ByteBuf which holds bytes for given string
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);

        //2. Create new slice of ByteBuf which starts at index 0 and ends at index 14
        ByteBuf sliced = buf.slice(0, 15);

        //3. Contains Netty in Action
        System.out.println(sliced.toString(utf8));
        //4. Update byte on index 0
        buf.setByte(0, (byte) 'J');
        //5. Wont fail as both ByteBuf share the same content and so modifications to one of them are visible on the other too
        assert buf.getByte(0) == sliced.getByte(0);
        System.out.println(sliced.toString(utf8));
    }

    public void copy() {
        Charset utf8 = Charset.forName("UTF-8");
        //1. Create ByteBuf which holds bytes for given string
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);

        //2. Create copy of ByteBuf which starts at index 0 and ends at index 14
        ByteBuf copy = buf.copy(0, 15);

        //3. Contains Netty in Action
        System.out.println(copy.toString(utf8));
        //4. Update byte on index 0
        buf.setByte(0, (byte) 'J');
        //5. Wont fail as both ByteBuf does not share the same content and so modifications to one of them are not shared
        assert buf.getByte(0) != copy.getByte(0);
        System.out.println(copy.toString(utf8));
    }

    //Relative operations on the ByteBuf
    public void getSet() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf =  Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        System.out.println((char)buf.getByte(0));
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();

        buf.setByte(0, (byte)'J');
        System.out.println((char)buf.getByte(0));

        assert readerIndex == buf.readerIndex();
        assert writerIndex == buf.writerIndex();
    }

    public void readWrite() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf =  Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        System.out.println(buf.readerIndex());
        System.out.println(buf.writerIndex());
        System.out.println((char)buf.readByte());
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();
        System.out.println(buf.readerIndex());
        System.out.println(buf.writerIndex());

        buf.writeByte((byte)'?');

        System.out.println(buf.readerIndex());
        System.out.println(writerIndex == buf.writerIndex());

        assert readerIndex == buf.readerIndex();
        assert writerIndex != buf.writerIndex();
    }
}
