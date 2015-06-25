package file;

import com.google.common.io.Closer;

import java.io.*;

/**
 * Created by edgar on 15-6-25.
 */
public class CloserExample {

    public static void main(String[] args) throws IOException {
        Closer closer = Closer.create();
        try {
            File dest = new File("Readme3.log");
            dest.deleteOnExit();

            BufferedReader reader = new BufferedReader(new FileReader("Readme.md"));
            BufferedWriter writer = new BufferedWriter(new FileWriter(dest));

            closer.register(reader);
            closer.register(writer);
        } catch (Throwable t) {
            throw closer.rethrow(t);
        } finally {
            closer.close();
        }
    }
}
