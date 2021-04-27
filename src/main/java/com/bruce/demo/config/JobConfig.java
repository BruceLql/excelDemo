package com.bruce.demo.config;

/**
 * @author 李启岚(起冉)
 */

import com.bruce.demo.service.task.SimpleJobDemo;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 全局作业配置中心
 */
@Configuration
public class JobConfig {

    @Autowired
    private ZookeeperRegistryCenter regCenter;
    @Autowired
    private LiteJobConfiguration liteJobConfiguration;
    @Autowired
    private SimpleJobDemo simpleJobDemo;

    @Bean(initMethod = "init")
    public JobScheduler simpleJobScheduler() {
        return new SpringJobScheduler(simpleJobDemo, regCenter, liteJobConfiguration);
    }
}