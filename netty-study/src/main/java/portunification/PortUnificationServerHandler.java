package portunification;

import factorial3.BigIntegerDecoder;
import factorial3.FactorialServerHandler;
import factorial3.NumberEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLContext;
import java.util.List;

/**
 * SSL的判断有问题，暂时屏蔽
 */
public class PortUnificationServerHandler extends ByteToMessageDecoder {

    private final boolean detectGzip;

    public PortUnificationServerHandler() {
        this(true);
    }

    private PortUnificationServerHandler(boolean detectGzip) {
        this.detectGzip = detectGzip;
    }
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 5) {
            return;
        }

        int magic1 = in.getUnsignedByte(in.readerIndex());
        int magic2 = in.getUnsignedByte(in.readerIndex() + 1);

        if (isGzip(magic1, magic2)) {
            enableGzip(ctx);
        } else if (isHttp(magic1, magic2)) {
            switchToHttp(ctx);
        } else if (isFactorial(magic1)) {
            switchToFactorial(ctx);
        } else {
            // Unknown protocol; discard everything and close the connection.
            in.clear();
            ctx.close();
        }

    }

    private void switchToFactorial(ChannelHandlerContext ctx) {
        ChannelPipeline p = ctx.pipeline();
        p.addLast("decoder", new BigIntegerDecoder());
        p.addLast("encoder", new NumberEncoder());
        p.addLast("handler", new FactorialServerHandler());
        p.remove(this);
    }

    private boolean isFactorial(int magic1) {
        return magic1 == 'F';
    }

    private void switchToHttp(ChannelHandlerContext ctx) {
        ChannelPipeline pipeline = ctx.pipeline();
        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("deflater", new HttpContentCompressor());
//        pipeline.addLast("handler", new HttpSnoo)

        pipeline.remove(this);
    }

    private boolean isHttp(int magic1, int magic2) {
        return
                magic1 == 'G' && magic2 == 'E' || // GET
                        magic1 == 'P' && magic2 == 'O' || // POST
                        magic1 == 'P' && magic2 == 'U' || // PUT
                        magic1 == 'H' && magic2 == 'E' || // HEAD
                        magic1 == 'O' && magic2 == 'P' || // OPTIONS
                        magic1 == 'P' && magic2 == 'A' || // PATCH
                        magic1 == 'D' && magic2 == 'E' || // DELETE
                        magic1 == 'T' && magic2 == 'R' || // TRACE
                        magic1 == 'C' && magic2 == 'O';   // CONNECT
    }

    private void enableGzip(ChannelHandlerContext ctx) {
        ChannelPipeline pipeline = ctx.pipeline();
        pipeline.addLast("gzipdeflater", ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP));
        pipeline.addLast("gzipinflater", ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));
        pipeline.addLast("unificationB", new PortUnificationServerHandler(false));
        pipeline.remove(this);
    }

    private boolean isGzip(int magic1, int magic2) {
        if (detectGzip) {
            return magic1 == 31 && magic2 == 139;
        }
        return false;
    }

}
