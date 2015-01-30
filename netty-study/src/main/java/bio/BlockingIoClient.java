package bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Administrator on 2015/1/27.
 */
public class BlockingIoClient {

    static final int remotePort = Integer.parseInt(System.getProperty("port", "8088"));
    static final String remoteHost = System.getProperty("host", "127.0.0.1");

    public static void main(String[] args) throws IOException {
        new BlockingIoClient().run(remoteHost, remotePort);
    }

    public void run(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String response;
        if ((response = in.readLine()) != null) {
            System.out.println("server say: " + response);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String clientString;
        while ((clientString = reader.readLine()) != null) {
            out.println(clientString);
            out.flush();
        }
    }

}
