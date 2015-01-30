package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2014/12/24.
 */
public class PlainNio2EchoServer {

    public void serve(int port) throws IOException {
        System.out.println("Listening for connections on port " + port);
        final AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(port);
        serverSocketChannel.bind(address);

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        //Start to accept new Client connections. Once one is accepted the CompletionHandler will get called.
        serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                serverSocketChannel.accept(null, this);
                ByteBuffer buffer = ByteBuffer.allocate(100);
                //Trigger a read operation on the Channel, the given CompletionHandler will be notified once something was read
                result.read(buffer, buffer, new EchoCompletionHandler(result));
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                try {
                    serverSocketChannel.close();
                } catch (IOException e) {
                } finally {
                    countDownLatch.countDown();
                }
            }
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private class EchoCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {
        private final AsynchronousSocketChannel channel;

        public EchoCompletionHandler(AsynchronousSocketChannel channel) {
            this.channel = channel;
        }

        @Override
        public void completed(Integer result, ByteBuffer attachment) {
            attachment.flip();
            //Trigger a write operation on the Channel, the given CompletionHandler will be notified once something was written
            channel.write(attachment, attachment, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    if (attachment.hasRemaining()) {
                        //Trigger again a write operation if something is left in the ByteBuffer
                        channel.write(attachment, attachment, this);
                    } else {
                        attachment.compact();
                        //Trigger a read operation on the Channel, the given CompletionHandler will be notified once something was read
                        channel.read(attachment, attachment, EchoCompletionHandler.this);
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    try {
                        channel.close();
                    } catch (IOException e) {
                        // ingnore on close
                    }
                }
            });
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
            try {
                channel.close();
            } catch (IOException e) {
                // ingnore on close
            }
        }
    }
}
