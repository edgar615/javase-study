package com.edgar.vertx.tcp;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;

/**
 * Created by Administrator on 2015/9/1.
 */
public class ServerExample {
    public static void main(String[] args) {
        //Creating a TCP server
//        Vertx.vertx().createNetServer();

        //Configuring a TCP server
        NetServerOptions options = new NetServerOptions().setPort(4321);
        NetServer server = Vertx.vertx().createNetServer(options);

        //Start the Server Listening
        server.listen();
        //server.listen(1234, "localhost");
        //The default host is 0.0.0.0 which means 'listen on all available addresses' and the default port is 0,
        // which is a special value that instructs the server to find a random unused local port and use that.


//        The actual bind is asynchronous so the server might not actually be listening until some time after the call to listen has returned.

//         If you want to be notified when the server is actually listening you can provide a handler to the listen call. For example:

        server = Vertx.vertx().createNetServer();
        server.listen(1234, "localhost", res -> {
            if (res.succeeded()) {
                System.out.println("Server is now listening!");
            } else {
                System.out.println("Failed to bind!");
            }
        });

        //Getting notified of incoming connections
        server = Vertx.vertx().createNetServer();
        server.connectHandler(socket -> {

        });

        //Reading data from the socket
        server = Vertx.vertx().createNetServer();
        server.connectHandler(socket -> {
            socket.handler(buffer -> {
                System.out.println("I received some bytes: " + buffer.length());
            });
        });

        //Writing data to a socket
//        Buffer buffer = Buffer.buffer().appendFloat(12.34f).appendInt(123);
//        socket.write(buffer);

// Write a string in UTF-8 encoding
//        socket.write("some data");

// Write a string using the specified encoding
//        socket.write("some data", "UTF-16");

        //Closed handler
//        socket.closeHandler(v -> {
//            System.out.println("The socket has been closed");
//        });

        //Sending files
        //socket.sendFile("myfile.dat");

        //Closing a TCP Server
        server.close(res -> {
            if (res.succeeded()) {
                System.out.println("Server is now closed");
            } else {
                System.out.println("close failed");
            }
        });

        //Scaling - sharing TCP servers
        //The handlers of any TCP server are always executed on the same event loop thread.
        //This means that if you are running on a server with a lot of cores, and you only have this one instance deployed then you will have at most one core utilised on your server.
        //In order to utilise more cores of your server you will need to deploy more instances of the server.

        for (int i = 0; i < 10; i ++) {
            NetServer server1 = Vertx.vertx().createNetServer();
            server1.connectHandler(socket -> {
               socket.handler(buffer -> {
                    socket.write(buffer);
               }) ;
            });
            server1.listen(1234, "localhost");
        }
        //或
//       vertx run com.mycompany.MyVerticle -instances 10
        //或
//        DeploymentOptions options = new DeploymentOptions().setInstances(10);
//        vertx.deployVerticle("com.mycompany.MyVerticle", options);


    }
}
