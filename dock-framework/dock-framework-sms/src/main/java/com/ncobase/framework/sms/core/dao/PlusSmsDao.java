package com.ncobase.framework.sms.core.dao;

import com.ncobase.framework.core.constant.GlobalConstants;
import com.ncobase.framework.redis.utils.RedisUtils;
import org.dromara.sms4j.api.dao.SmsDao;

import java.time.Duration;

/**
 * SmsDao 缓存配置 (使用框架自带 RedisUtils 实现 协议统一)
 * <p>主要用于短信重试和拦截的缓存
 *
 * @author Feng
 */
public class PlusSmsDao implements SmsDao {

    /**
     * 存储
     *
     * @param key       键
     * @param value     值
     * @param cacheTime 缓存时间（单位：秒)
     */
    @Override
    public void set(String key, Object value, long cacheTime) {
        RedisUtils.setCacheObject(GlobalConstants.GLOBAL_REDIS_KEY + key, value, Duration.ofSeconds(cacheTime));
    }

    /**
     * 存储
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void set(String key, Object value) {
        RedisUtils.setCacheObject(GlobalConstants.GLOBAL_REDIS_KEY + key, value, true);
    }

    /**
     * 读取
     *
     * @param key 键
     * @return 值
     */
    @Override
    public Object get(String key) {
        return RedisUtils.getCacheObject(GlobalConstants.GLOBAL_REDIS_KEY + key);
    }

    /**
     * remove
     * <p> 根据 key 移除缓存
     *
     * @param key 缓存键
     * @return 被删除的 value
     * @author :Wind
     */
    @Override
    public Object remove(String key) {
        return RedisUtils.deleteObject(GlobalConstants.GLOBAL_REDIS_KEY + key);
    }

    /**
     * 清空
     */
    @Override
    public void clean() {
        RedisUtils.deleteKeys(GlobalConstants.GLOBAL_REDIS_KEY + "sms:*");
    }

}
