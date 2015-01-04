package chapter04;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.CharsetUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2014/12/26.
 */
public class ChannelOperationExamples {

    public static void writingToChannel() {
        Channel channel = null; // Get the channel reference from somewhere
        //1. Create ByteBuf that holds data to write
        ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);
        //2. Write data
        ChannelFuture cf = channel.write(buf);
        //3. Add ChannelFutureListener to get notified after write completes
        cf.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    //4. Write operation completes without error
                    System.out.println("Write successful");
                } else {
                    //5. Write operation completed but because of error
                    System.err.println("Write error");
                    future.cause().printStackTrace();
                }
            }
        });
    }

    public static void writingToChannelManyThreads() {
        final Channel channel = null; // Get the channel reference from somewhere
        final ByteBuf buf = Unpooled.copiedBuffer("your data",
                CharsetUtil.UTF_8);
        Runnable writer = new Runnable() {
            @Override
            public void run() {
                channel.write(buf.duplicate());
            }
        };
        Executor executor = Executors.newCachedThreadPool();

        // write in one thread
        executor.execute(writer);

        // write in another thread
        executor.execute(writer);

    }
}
