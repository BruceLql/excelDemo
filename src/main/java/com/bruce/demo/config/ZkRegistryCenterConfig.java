package com.bruce.demo.config;

/**
 * @author 李启岚(起冉)
 */

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * zk注册中心配置
 */
@Configuration
public class ZkRegistryCenterConfig {

    @Value("${reg-center.server-list}")
    private String serverList;

    @Value("${reg-center.namespace}")
    private String namespace;

    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter regCenter() {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
    }

}