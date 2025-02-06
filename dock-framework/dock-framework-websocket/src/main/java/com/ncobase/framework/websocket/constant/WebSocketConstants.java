package com.ncobase.framework.websocket.constant;

/**
 * websocket 的常量配置
 *
 * @author zendwang
 */
public interface WebSocketConstants {

    /**
     * websocketSession 中的参数的 key
     */
    String LOGIN_USER_KEY = "loginUser";

    /**
     * 订阅的频道
     */
    String WEB_SOCKET_TOPIC = "global:websocket";

    /**
     * 前端心跳检查的命令
     */
    String PING = "ping";

    /**
     * 服务端心跳恢复的字符串
     */
    String PONG = "pong";
}
