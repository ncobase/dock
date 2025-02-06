package com.ncobase.demo.controller;

import com.ncobase.framework.core.domain.R;
import com.ncobase.framework.redis.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Redis 发布订阅 演示案例
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/demo/redis/pubsub")
public class RedisPubSubController {

    /**
     * 发布消息
     *
     * @param key   通道 Key
     * @param value 发送内容
     */
    @GetMapping("/pub")
    public R<Void> pub(String key, String value) {
        RedisUtils.publish(key, value, consumer -> {
            System.out.println("发布通道 => " + key + ", 发送值 => " + value);
        });
        return R.ok();
    }

    /**
     * 订阅消息
     *
     * @param key 通道 Key
     */
    @GetMapping("/sub")
    public R<Void> sub(String key) {
        RedisUtils.subscribe(key, String.class, msg -> {
            System.out.println("订阅通道 => " + key + ", 接收值 => " + msg);
        });
        return R.ok();
    }

}
