package com.ncobase.framework.social.config.properties;

import lombok.Data;

import java.util.List;

/**
 * 社交登录配置
 *
 * @author thiszhc
 */
@Data
public class SocialLoginConfigProperties {

    /**
     * 应用 ID
     */
    private String clientId;

    /**
     * 应用密钥
     */
    private String clientSecret;

    /**
     * 回调地址
     */
    private String redirectUri;

    /**
     * 是否获取 unionId
     */
    private boolean unionId;

    /**
     * Coding 企业名称
     */
    private String codingGroupName;

    /**
     * 支付宝公钥
     */
    private String alipayPublicKey;

    /**
     * 企业微信应用 ID
     */
    private String agentId;

    /**
     * stackoverflow api key
     */
    private String stackOverflowKey;

    /**
     * 设备 ID
     */
    private String deviceId;

    /**
     * 客户端系统类型
     */
    private String clientOsType;

    /**
     * maxkey 服务器地址
     */
    private String serverUrl;

    /**
     * 请求范围
     */
    private List<String> scopes;

}
