import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2014/12/11.
 */
public class URLPostExample {

    public static void main(String[] args) throws IOException {
        URL url = new URL("http://www.baidu.com");

        URLConnection urlConnection = url.openConnection();
        urlConnection.setDoOutput(true);
        OutputStream out = urlConnection.getOutputStream();

        out.write("Hello".getBytes());
        out.flush();

        InputStream in = urlConnection.getInputStream();
        int data = in.read();
        while(data != -1){
            System.out.print((char) data);
            data = in.read();
        }
        in.close();
    }
}
