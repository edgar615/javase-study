package channel;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2014/12/19.
 */
public class MapFile {

    public static void main(String[] args) throws Exception {
        File tempFile = File.createTempFile("mmaptest", null);
        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        FileChannel fc = raf.getChannel();
        ByteBuffer temp = ByteBuffer.allocate(100);

        temp.put("This is the file content".getBytes());
        temp.flip();

        fc.write(temp, 0);

        // Put something else in the file, starting at location 8192.
        // 8192 is 8 KB, almost certainly a different memory/FS page.
        // This may cause a file hole, depending on the
        // filesystem page size.
        temp.clear();
        temp.put("This is more file content".getBytes());
        temp.flip();
        fc.write(temp, 8192);

        MappedByteBuffer ro = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
        MappedByteBuffer rw = fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size());
        MappedByteBuffer cow = fc.map(FileChannel.MapMode.PRIVATE, 0, fc.size());

        System.out.println("Begin");
        showBuffers(ro, rw, cow);

        cow.position(8);
        cow.put("COW".getBytes());
        System.out.println("Change to COW buffer");
        showBuffers(ro, rw, cow);

        rw.position(9);
        rw.put(" R/W ".getBytes());
        rw.position(8194);
        rw.put(" R/W ".getBytes());
        rw.force();
        System.out.println("Change to R/W buffer");
        showBuffers(ro, rw, cow);

        temp.clear();
        temp.put("Channel write ".getBytes());
        temp.flip();
        fc.write(temp, 0);
        temp.rewind();
        fc.write(temp, 8202);
        System.out.println("Write on channel");
        showBuffers(ro, rw, cow);

        cow.position(8207);
        cow.put(" COW2 ".getBytes());
        System.out.println("Second change to COW buffer");
        showBuffers(ro, rw, cow);

        rw.position(0);
        rw.put(" R/W2 ".getBytes());
        rw.position(8210);
        rw.put(" R/W2 ".getBytes());
        rw.force();
        System.out.println("Second change to R/W buffer");
        showBuffers(ro, rw, cow);

        fc.close();
        raf.close();
        tempFile.delete();
    }

    public static void showBuffers(ByteBuffer ro, ByteBuffer rw, ByteBuffer cow)
            throws Exception {
        dumpBuffer("R/O", ro);
        dumpBuffer("R/W", rw);
        dumpBuffer("COW", cow);
        System.out.println("");
    }

    public static void dumpBuffer(String prefix, ByteBuffer buffer) throws Exception {
        System.out.print(prefix + ": '");
        int nulls = 0;
        int limit = buffer.limit();
        for (int i = 0; i < limit; i++) {
            char c = (char) buffer.get(i);
            if (c == '\u0000') {
                nulls++;
                continue;
            }
            if (nulls != 0) {
                System.out.print("|[" + nulls + " nulls]|");
                nulls = 0;
            }
            System.out.print(c);
        }
        System.out.println("'");
    }
}
