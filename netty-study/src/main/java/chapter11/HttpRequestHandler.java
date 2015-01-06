package chapter11;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.RandomAccessFile;

/**
 * Created by Administrator on 2015/1/6.
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private String wsUri;

    public HttpRequestHandler(String wsUri) {
        this.wsUri = wsUri;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        //Check if the request is an WebSocket Upgrade request and if so retain it and pass it to the next ChannelInboundHandler in the ChannelPipeline.
        if (wsUri.equalsIgnoreCase(request.getUri())) {
            ctx.fireChannelRead(request.retain());
        } else {
            if (HttpHeaders.is100ContinueExpected(request)) {
                //Handle 100 Continue requests to conform HTTP 1.1
                send100Continue(ctx);
            }

            RandomAccessFile file = new RandomAccessFile("D:\\dev\\workspace\\javase-study\\netty-study\\src\\main\\resources\\index.html", "r");
            HttpResponse response = new DefaultHttpResponse(request.getProtocolVersion(), HttpResponseStatus.OK);
            response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plan; charset=UTF-8");

            boolean keeplive = HttpHeaders.isKeepAlive(request);
            //Add needed headers depending of if keepalive is used or not
            if (keeplive) {
                response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, file.length());
                response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            }

            //Write the HttpRequest to the client. Be aware that we use a HttpRequest and not a FullHttpRequest as it is only the first part of the request. Also we not use writeAndFlush(..) as this should be done later
            ctx.write(response);

            //Write the index.html to the client. Depending on if SslHandler is in the ChannelPipeline use DefaultFileRegion or ChunkedNioFile
            if (ctx.pipeline().get(SslHandler.class) == null) {
                ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
            } else {
                ctx.write(new ChunkedNioFile(file.getChannel()));
            }
            //Write and flush the LastHttpContent to the client which marks the requests as complete
            ChannelFuture cf = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            //Depending on if keepalive is used close the Channel after the write completes
            if (!keeplive) {
                cf.addListener(ChannelFutureListener.CLOSE);
            }

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }

}
