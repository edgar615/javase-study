package com.edgar.curator.service;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;

/**
 * Created by Administrator on 2015/9/22.
 */
public class ServiceManager {
    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.149.131:2181", new RetryNTimes(5, 1000));
        client.start();

        ServiceDiscovery serviceDiscovery = ServiceDiscoveryBuilder.builder(Void.class)
                .basePath("load-balancing-example")
                .client(client).build();
        serviceDiscovery.start();

        ServiceProvider provider = serviceDiscovery.serviceProviderBuilder().serviceName("api").build();
        provider.start();

        ServiceInstance instance = provider.getInstance();
        String address = instance.buildUriSpec();
        //String response = //address + "/api";
        System.out.println(address + "/api");

    }
}
