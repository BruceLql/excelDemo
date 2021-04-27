package com.bruce.demo.service.task;

/**
 * @author 李启岚(起冉)
 */

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SimpleJobDemo作业配置
 */
@Configuration
public class SimpleJobDemoJobProperties {

    @Value("${simpleJobDemo.cron}")
    private String cron;

    @Value("${simpleJobDemo.sharding-total-count}")
    private int shardingTotalCount;

    @Value("${simpleJobDemo.sharding-item-parameters}")
    private String shardingItemParameters;

    @Value("${simpleJobDemo.job-description}")
    private String jobDescription;

    @Value("${simpleJobDemo.job-parameter}")
    private String jobParameter;

    @Autowired
    private SimpleJobDemo simpleJobDemo;

    @Bean
    public LiteJobConfiguration liteJobConfiguration() {

        JobCoreConfiguration.Builder builder =
                JobCoreConfiguration.newBuilder(simpleJobDemo.getClass().getName(), cron, shardingTotalCount);

        JobCoreConfiguration jobCoreConfiguration = builder
                .shardingItemParameters(shardingItemParameters)
                .description(jobDescription)
                .jobParameter(jobParameter)
                .build();

        SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(jobCoreConfiguration, simpleJobDemo.getClass().getCanonicalName());
        return LiteJobConfiguration
                .newBuilder(simpleJobConfiguration)
                .overwrite(true)
                .build();
    }

}

