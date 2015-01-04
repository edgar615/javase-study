package chapter04;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Administrator on 2014/12/26.
 */
public class PlainNioServer {
    public void serve(int port) throws IOException {
        System.out.println("Listening for connections on port " + port);
        ServerSocketChannel serverSocketChannel;
        Selector selector;

        serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        InetSocketAddress address = new InetSocketAddress(port);
        //1. Bind server to port
        serverSocket.bind(address);
        serverSocketChannel.configureBlocking(false);
        //2. Open selector that handles channels
        selector = Selector.open();
        //3. Register erverSocket to selector and specify that it is interested in new accepted clients
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());

        while (true) {
            try {
                //4. Wait for new events that are ready for process. This will block until something happens
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            //5. Obtain all SelectionKey instances that received events
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                try {
                    //6. Check if event was because new client ready to get accepted
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        System.out.println("Accepted connection from " + client);
                        client.configureBlocking(false);
                        //7. Accept client and register it to selector
                        client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, msg.duplicate());
                    }
                    //8. Check if event was because socket is ready to write data
                    if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        //9. Write data to connected client. This may not write all the data if the network is saturated. If so it will pick up the not-written data and write it once the network is writable again.
                        while (buffer.hasRemaining()) {
                            if (client.write(buffer) == 0) {
                                break;
                            }
                        }
                        //10. Close connection
                        client.close();
                    }
                } catch (IOException e) {
                    key.cancel();
                    try {
                        key.channel().close();
                    } catch (IOException ex) {

                    }
                }
            }
        }
    }
}
