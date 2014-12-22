package channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2014/12/22.
 */
public class TimeClient {
    private static final int DEFAULT_TIME_PORT = 37;
    private static final long DIFF_1900 = 2208988800L;
    protected int port = DEFAULT_TIME_PORT;
    protected List remoteHosts;
    protected DatagramChannel channel;

    public TimeClient() throws IOException {
        this.channel = DatagramChannel.open();
    }

    protected InetSocketAddress receivePacket(DatagramChannel channel, ByteBuffer buffer) throws IOException {
        buffer.clear();
        return (InetSocketAddress) channel.receive(buffer);
    }

    protected void sendRequests() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        Iterator it = remoteHosts.iterator();
        while (it.hasNext()) {
            InetSocketAddress addr = (InetSocketAddress) it.next();
            System.out.println("Requesting time from " + addr.getHostName() + ":" + addr.getPort());
            buffer.clear().flip();
            channel.send(buffer, addr);
        }
    }

//    protected void getReplies() {
//        ByteBuffer longBuffer = ByteBuffer.allocate(8);
//        longBuffer.order(ByteOrder.BIG_ENDIAN);
//        longBuffer.putLong(0, 0);
//        longBuffer.position(4);
//
//        ByteBuffer buffer = longBuffer.slice();
//        int expect = remoteHosts.size();
//        int replies = 0;
//        System.out.println("");
//        System.out.println("Waiting for replies...");
//
//        while (true) {
//            InetSocketAddress sa;
//            sa = receivePacket(channel, buffer);
//            buffer.flip();
//            replies++;
//            printTime(longBuffer.getLong(0), sa);
//            if (replies == expect) {
//                System.out.println("All packets answered");
//                break;
//            }
//            System.out.println ("Received " + replies + " of " + expect + " replies");
//        }
//    }
}
