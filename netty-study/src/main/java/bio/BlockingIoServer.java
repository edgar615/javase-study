package bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2015/1/27.
 */
public class BlockingIoServer {

    static final int port = Integer.parseInt(System.getProperty("port", "8088"));

    public static void main(String[] args) throws IOException {
        new BlockingIoServer().serve(port);
    }

    public void serve(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accept connection from " + clientSocket);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("Hello " + clientSocket.getInetAddress().getHostAddress());
            String request;
            String response;

            while ((request = in.readLine()) != null) {
                if ("Done".equals(request)) {
                    break;
                }
                response = processRequest(request);
                out.println(response);
            }
        }
    }
    private String processRequest(String request) {
        System.out.println("client say: " + request);
        return "server process [" + request + "]";
    }
}
