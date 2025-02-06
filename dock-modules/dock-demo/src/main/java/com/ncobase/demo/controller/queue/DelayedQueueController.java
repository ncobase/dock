package com.ncobase.demo.controller.queue;

import cn.dev33.satoken.annotation.SaIgnore;
import com.ncobase.framework.core.domain.R;
import com.ncobase.framework.redis.utils.QueueUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 延迟队列 演示案例
 * <p>
 * 轻量级队列 重量级数据量 请使用 MQ
 * 例如：创建订单 30 分钟后过期处理
 * <p>
 * 集群测试通过 同一个数据只会被消费一次 做好事务补偿
 * 集群测试流程 两台集群分别开启订阅 在其中一台发送数据 观察接收消息的规律
 *
 * @author Lion Li
 * @version 3.6.0
 */
@SaIgnore
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/demo/queue/delayed")
public class DelayedQueueController {

    /**
     * 订阅队列
     *
     * @param queueName 队列名
     */
    @GetMapping("/subscribe")
    public R<Void> subscribe(String queueName) {
        log.info("通道：{} 监听中......", queueName);
        // 项目初始化设置一次即可
        QueueUtils.subscribeBlockingQueue(queueName, (String orderNum) -> {
            // 观察接收时间
            log.info("通道：{}, 收到数据：{}", queueName, orderNum);
            return CompletableFuture.runAsync(() -> {
                // 异步处理数据逻辑 不要在上方处理业务逻辑
                log.info("数据处理：{}", orderNum);
            });
        }, true);
        return R.ok();
    }

    /**
     * 添加队列数据
     *
     * @param queueName 队列名
     * @param orderNum  订单号
     * @param time      延迟时间 (秒)
     */
    @GetMapping("/add")
    public R<Void> add(String queueName, String orderNum, Long time) {
        QueueUtils.addDelayedQueueObject(queueName, orderNum, time, TimeUnit.SECONDS);
        // 观察发送时间
        log.info("通道：{} , 发送数据：{}", queueName, orderNum);
        return R.ok();
    }

    /**
     * 删除队列数据
     *
     * @param queueName 队列名
     * @param orderNum  订单号
     */
    @GetMapping("/remove")
    public R<Void> remove(String queueName, String orderNum) {
        if (QueueUtils.removeDelayedQueueObject(queueName, orderNum)) {
            log.info("通道：{} , 删除数据：{}", queueName, orderNum);
        } else {
            return R.fail();
        }
        return R.ok();
    }

    /**
     * 销毁队列
     *
     * @param queueName 队列名
     */
    @GetMapping("/destroy")
    public R<Void> destroy(String queueName) {
        // 用完了一定要销毁 否则会一直存在
        QueueUtils.destroyDelayedQueue(queueName);
        return R.ok();
    }

}
