import com.google.common.collect.ImmutableMap;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/22.
 */
public class Worker {
  public static void main(String[] args) throws Exception {

//        String workerName = args[0];
//        int port = Integer.parseInt(args[1]);
    Vertx vertx = Vertx.vertx();
    Router router = Router.router(vertx);
    router.route().handler(rc -> {
      System.out.println("Work done by " + "worker");
      rc.response().end("Work done by " + "worker");
    });

    int port = 2589;
    vertx.createHttpServer().requestHandler(router::accept).listen(port);

    CuratorFramework client = CuratorFrameworkFactory.newClient("10.4.7.48:2181", new
            RetryNTimes(5, 1000));
    client.start();

    Map<String, Object> map = new HashMap<>();
    map.put("foo", "bar");
    ServiceInstance instance = ServiceInstance.builder()
            .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
//                .address(InetAddress.getLocalHost().getHostAddress())
            .port(port)
            .payload(map)
            .name("worker").build();

    ServiceDiscoveryBuilder.builder(Void.class)
            .basePath("load-balancing-example")
            .client(client)
            .thisInstance(instance)
            .build().start();
  }
}
