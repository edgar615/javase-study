import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/9/22.
 */
public class Main {

  public static void main(String[] args) throws Exception {
    CuratorFramework client = CuratorFrameworkFactory.newClient("10.4.7.48:2181", new
            RetryNTimes(5, 1000));
    client.start();

    ServiceDiscovery discovery = ServiceDiscoveryBuilder.builder(Void.class)
            .basePath("csst-microservice")
            .client(client)
            .build();
    discovery.start();

    ServiceProvider provider = discovery.serviceProviderBuilder().serviceName("task")
            .build();
    provider.start();

    for (int i = 0; i < 100; i++) {
      ServiceInstance instance = provider.getInstance();
      System.out.println(instance);
      if (instance != null) {
        String address = instance.getAddress();
        System.out.println("get : " + instance.getAddress() + ":" + instance.getPort());
      }

      TimeUnit.SECONDS.sleep(1);
//            Vertx.vertx().createHttpClient().getNow(instance.getPort(), instance.getAddress(),
// "/", response -> {
//                response.bodyHandler(body -> {
//                    System.out.println(body.toString());
//                });
//            });
    }
  }
}
