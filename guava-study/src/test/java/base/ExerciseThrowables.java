package base;

import com.google.common.base.Throwables;

import java.io.InputStream;
import java.net.URL;
import java.net.UnknownHostException;

public class ExerciseThrowables {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://ociweb.comd");
            final InputStream in = url.openStream();
            // read from the input stream
            in.close();
        } catch (Throwable t) {
            throw Throwables.propagate(t);
        }
    }
}