package chapter01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2014/12/24.
 */
public class PlainEchoServer {

    public static void main(String[] args) throws IOException {
        new PlainEchoServer().serve(1234);
    }

    public void serve(int port) throws IOException {
        final ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            final Socket clientSocket = serverSocket.accept();
            System.out.println("Accept connection from " + clientSocket);
            new Thread() {
                @Override
                public void run() {
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                        writer.println("Hello " + clientSocket);
                        String fromClient;
                        while ((fromClient = reader.readLine()) != null) {
                            System.out.println(fromClient);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        try {
                            clientSocket.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    }
}
