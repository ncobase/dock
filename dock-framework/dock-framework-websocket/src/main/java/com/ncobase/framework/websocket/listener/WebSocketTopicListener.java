package com.ncobase.framework.websocket.listener;

import cn.hutool.core.collection.CollUtil;
import com.ncobase.framework.websocket.holder.WebSocketSessionHolder;
import com.ncobase.framework.websocket.utils.WebSocketUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;

/**
 * WebSocket 主题订阅监听器
 *
 * @author zendwang
 */
@Slf4j
public class WebSocketTopicListener implements ApplicationRunner, Ordered {

    /**
     * 在 Spring Boot 应用程序启动时初始化 WebSocket 主题订阅监听器
     *
     * @param args 应用程序参数
     * @throws Exception 初始化过程中可能抛出的异常
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 订阅 WebSocket 消息
        WebSocketUtils.subscribeMessage((message) -> {
            log.info("WebSocket 主题订阅收到消息 session keys={} message={}", message.getSessionKeys(), message.getMessage());
            // 如果 key 不为空就按照 key 发消息 如果为空就群发
            if (CollUtil.isNotEmpty(message.getSessionKeys())) {
                message.getSessionKeys().forEach(key -> {
                    if (WebSocketSessionHolder.existSession(key)) {
                        WebSocketUtils.sendMessage(key, message.getMessage());
                    }
                });
            } else {
                WebSocketSessionHolder.getSessionsAll().forEach(key -> {
                    WebSocketUtils.sendMessage(key, message.getMessage());
                });
            }
        });
        log.info("初始化 WebSocket 主题订阅监听器成功");
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
