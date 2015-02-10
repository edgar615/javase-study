package io;

import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;

/**
 * Created by Administrator on 2015/2/10.
 */
public class FilesTest {

    // 	ByteSource、 	ByteSink

    @Test
    public void testByte() {
        Files.asByteSink(new File("d:\1.txt"));
    }

    // 	CharSource、 	CharSink
}
