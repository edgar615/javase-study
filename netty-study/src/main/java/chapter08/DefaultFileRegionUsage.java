package chapter08;

import io.netty.channel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Administrator on 2014/12/31.
 */
public class DefaultFileRegionUsage {
    public static void transfer(Channel channel, File file) throws FileNotFoundException {
        //Get FileInputStream on file
        FileInputStream in = new FileInputStream(file);
        //Create a new DefaultFileRegion for the file starting at offset 0 and ending at the end of the file
        FileRegion region = new DefaultFileRegion(in.getChannel(), 0, file.length());

        //Send the DefaultFileRegion and register a ChannelFutureListener
        channel.writeAndFlush(region).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                //Handle failure during send
                if (!future.isSuccess()) {
                    Throwable cause = future.cause();
                    // Do something
                }
            }
        });
    }
}
