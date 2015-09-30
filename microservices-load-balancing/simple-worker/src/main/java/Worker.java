import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;

import java.net.InetAddress;

/**
 * Created by Administrator on 2015/9/22.
 */
public class Worker {
    public static void main(String[] args) throws Exception {

        String workerName = args[0];
        int port = Integer.parseInt(args[1]);
        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);
        router.route().handler(rc -> {
            System.out.println("Work done by " + workerName);
            rc.response().end("Work done by " + workerName);
        });

        vertx.createHttpServer().requestHandler(router::accept).listen(port);

        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.149.131:2181", new RetryNTimes(5, 1000));
        client.start();

        ServiceInstance instance = ServiceInstance.builder()
                .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
//                .address(InetAddress.getLocalHost().getHostAddress())
                .port(port)
                .name("worker").build();

        ServiceDiscoveryBuilder.builder(Void.class)
                .basePath("load-balancing-example")
                .client(client)
                .thisInstance(instance)
                .build().start();
    }
}
