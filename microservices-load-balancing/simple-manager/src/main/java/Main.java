import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/9/22.
 */
public class Main {

  public static void main(String[] args) throws Exception {
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    CuratorFramework client = CuratorFrameworkFactory.newClient("10.4.7.220:2181", new
            RetryNTimes(5, 1000));
    client.start();

    ServiceDiscovery<String> discovery = ServiceDiscoveryBuilder.<String>builder(String.class)
            .basePath("/discovery")
            .client(client)
            .build();
    discovery.start();

    ServiceProvider provider = discovery.serviceProviderBuilder().serviceName("hoho")
            .build();
    provider.start();

    executorService.scheduleAtFixedRate(new Runnable() {
      @Override
      public void run() {
        try {
          ServiceInstance instance = provider.getInstance();
          System.out.println(instance);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }, 1, 1, TimeUnit.SECONDS);

  }
}
