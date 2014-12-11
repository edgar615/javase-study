import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2014/12/11.
 */
public class URLExample {

    public static void main(String[] args) throws IOException {
        URL url = new URL("http://www.baidu.com");

        URLConnection urlConnection = url.openConnection();
        InputStream in = urlConnection.getInputStream();
        int data = in.read();
        while(data != -1){
            System.out.print((char) data);
            data = in.read();
        }
        in.close();
    }
}
