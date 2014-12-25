package chapter01;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2014/12/24.
 */
public class PlainEchoClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("127.0.0.1", 1234);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String fromServer;
        if ((fromServer = in.readLine()) != null) {
            System.out.println(fromServer);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String clientString;
        while ((clientString = reader.readLine()) != null) {
            out.println(clientString);
            out.flush();
        }


    }
}
