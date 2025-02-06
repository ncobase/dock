package com.ncobase.framework.sse.listener;

import cn.hutool.core.collection.CollUtil;
import com.ncobase.framework.sse.core.SseEmitterManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;

/**
 * SSE 主题订阅监听器
 *
 * @author Lion Li
 */
@Slf4j
public class SseTopicListener implements ApplicationRunner, Ordered {

    @Autowired
    private SseEmitterManager sseEmitterManager;

    /**
     * 在 Spring Boot 应用程序启动时初始化 SSE 主题订阅监听器
     *
     * @param args 应用程序参数
     * @throws Exception 初始化过程中可能抛出的异常
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        sseEmitterManager.subscribeMessage((message) -> {
            log.info("SSE 主题订阅收到消息 session keys={} message={}", message.getUserIds(), message.getMessage());
            // 如果 key 不为空就按照 key 发消息 如果为空就群发
            if (CollUtil.isNotEmpty(message.getUserIds())) {
                message.getUserIds().forEach(key -> {
                    sseEmitterManager.sendMessage(key, message.getMessage());
                });
            } else {
                sseEmitterManager.sendMessage(message.getMessage());
            }
        });
        log.info("初始化 SSE 主题订阅监听器成功");
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
