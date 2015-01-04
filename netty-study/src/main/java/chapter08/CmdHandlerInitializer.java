package chapter08;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * Created by Administrator on 2014/12/31.
 */
public class CmdHandlerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new CmdDecoder(65 * 1024))//Add the CmdDecoder that will extract the command and forward to the next handler in the pipeline
                .addLast(new CmdHandler());//Add CmdHandler that will receive the commands
    }

    public static final class Cmd {
        private final ByteBuf name;
        private final ByteBuf args;

        public Cmd(ByteBuf name, ByteBuf args) {
            this.name = name;
            this.args = args;
        }

        public ByteBuf name() {
            return name;
        }

        public ByteBuf args() {
            return args;
        }
    }

    public static final class CmdDecoder extends LineBasedFrameDecoder {

        public CmdDecoder(int maxLength) {
            super(maxLength);
        }

        @Override
        protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
            //Extract the ByteBuf frame which was delimited by the \r\n
            ByteBuf frame =  (ByteBuf) super.decode(ctx, buffer);
            if (frame == null) {//There was no full frame ready so just return null
                return null;
            }
            //Fix the first whitespace as its the separator of the name and arguments
            int index = frame.indexOf(frame.readerIndex(), frame.writerIndex(), (byte) ' ');
            //Construct the command by passing in the name and the arguments using the index
            return new Cmd(frame.slice(frame.readerIndex(), index), frame.slice(index +1, frame.writerIndex()));
        }
    }

    private static class CmdHandler extends ChannelHandlerAdapter {

    }

}
