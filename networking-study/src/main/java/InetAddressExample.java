import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2014/12/11.
 */
public class InetAddressExample {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName("www.baidu.com");
        InetAddress address = InetAddress.getByName("10.4.7.232");
        InetAddress localhost = InetAddress.getLocalHost();
    }
}
