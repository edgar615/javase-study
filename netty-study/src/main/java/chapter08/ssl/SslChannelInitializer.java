package chapter08.ssl;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

/**
 * Created by Administrator on 2014/12/31.
 */
public class SslChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final SSLContext context;
    private final boolean client;
    private final boolean startTls;

    public SslChannelInitializer(SSLContext context, boolean client, boolean startTls) {
        this.context = context;
        this.client = client;
        this.startTls = startTls;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //Obtain a new SslEngine from the SslContext. Use a new SslEngine for each SslHandler instance
        SSLEngine engine = context.createSSLEngine();
        //Set if the SslEngine is used in client or server mode
        engine.setUseClientMode(true);
        //Add the SslHandler in the pipeline as first handler
        //the  SslHandler must  be  the  first ChannelHandler in the ChannelPipeline.
        ch.pipeline().addFirst("ssl", new SslHandler(engine, startTls));
    }
}
