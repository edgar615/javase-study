package com.edgar.curator.service;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;

/**
 * http://sleeplessinslc.blogspot.com/2014/09/dynamic-service-discovery-with-apache.html
 * http://www.cnblogs.com/hupengcool/p/3976362.html
 */
public class ServiceRegister {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.149.131:2181", new RetryNTimes(5, 1000));
        client.start();

        ServiceInstance serviceInstance  = ServiceInstance.builder()
                .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
                .address("10.4.7.15")
                .port(8080)
                .name("api")
                .build();

        ServiceDiscoveryBuilder.builder(Void.class).basePath("load-balancing-example")
                .client(client)
                .thisInstance(serviceInstance)
                .build().start();
    }
}
