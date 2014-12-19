package channel;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Created by Administrator on 2014/12/19.
 */
public class ChannelTransfer {

    public static void main(String[] args) throws Exception {
        catFiles (Channels.newChannel (System.out), new String[] {"blahblah.txt", "MappedHttp.out"});
    }

    private static void catFiles(WritableByteChannel target, String[] files) throws Exception {
        for (int i = 0; i < files.length; i++) {
            FileInputStream fis = new FileInputStream(files[i]);
            FileChannel channel = fis.getChannel();
            channel.transferTo(0, channel.size(), target);
            channel.close();
            fis.close();
        }
    }
}
