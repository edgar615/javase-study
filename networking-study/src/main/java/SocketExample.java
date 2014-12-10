import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Administrator on 2014/12/10.
 */
public class SocketExample {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("10.4.7.15", 9999);
        OutputStream out = socket.getOutputStream();
        out.write("Client Request".getBytes());
        out.flush();
        out.close();

//        InputStream in = socket.getInputStream();
//        int data = -1;
//        while ((data = in.read()) != -1) {
//            System.out.print((char)data);
//            data = in.read();
//        }
//        in.close();

//        socket.close();
    }
}
