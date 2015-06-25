package file;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.google.common.io.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.List;

/**
 * Created by edgar on 15-6-25.
 */
public class FilesTest {

    @Test
    public void testCopy() throws IOException {
        File original = new File("Readme.md");
        File copy = new File("Readme.log");
        Files.copy(original, copy);
    }

    @Test
    public void testMove() throws IOException {
        File original = new File("Readme.log");
        File copy = new File("Readme2.log");
        Files.move(original, copy);
    }

    @Test
    public void testReadFIleIntoListOfStrings() throws IOException {
        File file = new File("Readme2.log");
        List<String> lines = Files.readLines(file, Charsets.UTF_8);
        for (String line : lines) {
            System.out.println(line);
        }
    }

    @Test
    public void testReadFIleIntoListOfStringsWithProcessor() throws IOException {
        File file = new File("Readme2.log");
        List<String> lines = Files.readLines(file, Charsets.UTF_8, new ListLineProcessor());
        for (String line : lines) {
            System.out.println(line);
        }
    }

    @Test
    public void testHashFile() throws IOException {
        File file = new File("Readme2.log");
        System.out.println(Files.hash(file, Hashing.md5()));
    }

    @Test
    public void testAppending() throws IOException {
        File file = new File("Readme3.log");
        file.deleteOnExit();

        String start = "To be, or not to be";
        Files.write(start, file, Charsets.UTF_8);
        System.out.println(Files.toString(file, Charsets.UTF_8));

        String end = ",that is the question";
        Files.append(end, file, Charsets.UTF_8);
        System.out.println(Files.toString(file, Charsets.UTF_8));

        String overwrite = "Overwriting the file";
        Files.write(overwrite, file, Charsets.UTF_8);
        System.out.println(Files.toString(file, Charsets.UTF_8));

    }

    @Test
    public void testByteSource() throws IOException {
        File file = new File("Readme2.log");
        ByteSource byteSource = Files.asByteSource(file);
        byte[] readBytes = byteSource.read();
        Assert.assertArrayEquals(readBytes, Files.toByteArray(file));
    }

    @Test
    public void testByteSink() throws IOException {
        File dest = new File("bytesink.log");
        dest.deleteOnExit();
        ByteSink byteSink = Files.asByteSink(dest);
        File file = new File("Readme2.log");
        byteSink.write(Files.toByteArray(file));
        Assert.assertArrayEquals(Files.toByteArray(dest), Files.toByteArray(file));
    }

    @Test
    public void testCopyByteSink() throws IOException {
        File dest = new File("CopyByteSink.log");
        dest.deleteOnExit();
        File source = new File("Readme2.log");
        ByteSource byteSource = Files.asByteSource(source);
        ByteSink byteSink = Files.asByteSink(dest);
        byteSource.copyTo(byteSink);

        Assert.assertArrayEquals(Files.toByteArray(dest), Files.toByteArray(source));
    }

    @Test
    public void testLimitByteStream() throws IOException {
        File file = new File("Readme2.log");

        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        InputStream in = ByteStreams.limit(inputStream, 10);

        Assert.assertEquals(in.available(), 10);
        System.out.println(inputStream.available());
    }

    @Test
    public void testEncodeDecode() throws IOException {
        File file = new File("Readme2.log");
        byte[] bytes = Files.toByteArray(file);
        BaseEncoding baseEncoding = BaseEncoding.base64();
        String encoded = baseEncoding.encode(bytes);
        System.out.println(encoded);
        System.out.println(baseEncoding.decode(encoded));
    }

    @Test
    public void testEncodeByteSink() throws IOException {
        File file = new File("Readme2.log");
        File encodeFile = new File("encoded.log");
        encodeFile.deleteOnExit();

        CharSink charSink = Files.asCharSink(encodeFile, Charsets.UTF_8);

        BaseEncoding baseEncoding = BaseEncoding.base64();
        ByteSink byteSink = baseEncoding.encodingSink(charSink);
        ByteSource byteSource = Files.asByteSource(file);
        byteSource.copyTo(byteSink);


        String encodedBytes = baseEncoding.encode(byteSource.read());
        System.out.println(encodedBytes);
        Assert.assertEquals(encodedBytes, Files.toString(encodeFile, Charsets.UTF_8));
    }
}
