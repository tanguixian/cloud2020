package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface LoadBalancer {
    //ServiceInstance服务实例 把可以使用服务加入到list里面
    ServiceInstance instances(List<ServiceInstance> serviceInstances);
}
