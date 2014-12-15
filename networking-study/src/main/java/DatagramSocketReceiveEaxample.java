import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Administrator on 2014/12/11.
 */
public class DatagramSocketReceiveEaxample {

    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(9999);

        byte[] buffer = new byte[10];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        datagramSocket.receive(packet);

        System.out.println(new String(packet.getData()));
    }
}
