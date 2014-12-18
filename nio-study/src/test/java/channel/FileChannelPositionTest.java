package channel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2014/12/18.
 */
public class FileChannelPositionTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile file = new RandomAccessFile("blahblah.txt", "rw");
        file.seek(100);

        FileChannel fileChannel = file.getChannel();
        System.out.println ("file pos: " + fileChannel.position( ));

        file.seek(150);
        System.out.println ("file pos: " + fileChannel.position( ));

        fileChannel.position(200);
        System.out.println ("file pos: " + file.getFilePointer());
    }
}
