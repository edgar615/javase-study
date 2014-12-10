import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2014/12/10.
 */
public class ServerSocketExample {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);

        boolean isStopped = false;
        while (!isStopped) {
            Socket request = serverSocket.accept();
            handleRequest(request);
//            request.close();
        }
        serverSocket.close();
    }

    private static void handleRequest(Socket request) throws IOException {
        InputStream in = request.getInputStream();
        int data = in.read();
        while (data != -1) {
            System.out.print((char)data);
            data = in.read();
        }
        in.close();

//        OutputStream out = request.getOutputStream();
//        out.write("Hello World!".getBytes());
//        out.flush();
    }
}
