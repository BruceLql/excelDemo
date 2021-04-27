package com.bruce.demo.service.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 李启岚(起冉)
 */
@Component
@Slf4j
public class SimpleJobDemo implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {

        log.info("shardingContext的信息为：" + shardingContext);

        switch (shardingContext.getShardingItem()) {
            case 0:
                System.out.println("上海分片执行的任务：求和1到3完成，结果为：6");
                break;
            case 1:
                System.out.println("北京分片执行的任务：求和4到7完成，结果为：22");
                break;
            case 2:
                System.out.println("深圳分片执行的任务：求和8到10完成，结果为：27");
                break;
        }


    }
}
