import java.io.IOException;
import java.net.*;

/**
 * Created by Administrator on 2014/12/11.
 */
public class DatagramSocketSendEaxample {

    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket();

        byte[] buffer = "0123456789".getBytes();
        InetAddress receiverAddress = InetAddress.getLocalHost();

        DatagramPacket packet = new DatagramPacket(
                buffer, buffer.length, receiverAddress, 9999);
        datagramSocket.send(packet);
    }
}
