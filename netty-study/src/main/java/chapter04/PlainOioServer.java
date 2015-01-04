package chapter04;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2014/12/26.
 */
public class PlainOioServer {
    public void serve(int port) throws IOException {
        //1. Bind server to port
        final ServerSocket serverSocket = new ServerSocket(port);
        try {
            while (true) {
                //2. Accept connection
                final Socket client = serverSocket.accept();
                System.out.println("Accepted connection from " + client);
                //3. Create new thread to handle connection
                new Thread() {
                    @Override
                    public void run() {
                        OutputStream out;
                        try {
                            //4. Write message to connected client
                            out = client.getOutputStream();
                            out.write("Hi!\r\n".getBytes(Charset.forName("UTF-8")));
                            out.flush();
                            //5. Close connection once message written and flushed
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
