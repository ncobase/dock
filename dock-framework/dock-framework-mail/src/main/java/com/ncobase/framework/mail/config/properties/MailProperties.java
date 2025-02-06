package com.ncobase.framework.mail.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JavaMail 配置属性
 *
 * @author Michelle.Chung
 */
@Data
@ConfigurationProperties(prefix = "mail")
public class MailProperties {

    /**
     * 过滤开关
     */
    private Boolean enabled;

    /**
     * SMTP 服务器域名
     */
    private String host;

    /**
     * SMTP 服务端口
     */
    private Integer port;

    /**
     * 是否需要用户名密码验证
     */
    private Boolean auth;

    /**
     * 用户名
     */
    private String user;

    /**
     * 密码
     */
    private String pass;

    /**
     * 发送方，遵循 RFC-822 标准<br>
     * 发件人可以是以下形式：
     *
     * <pre>
     * 1. user@xxx.xx
     * 2.  name &lt;user@xxx.xx&gt;
     * </pre>
     */
    private String from;

    /**
     * 使用 STARTTLS 安全连接，STARTTLS 是对纯文本通信协议的扩展。它将纯文本连接升级为加密连接（TLS 或 SSL），而不是使用一个单独的加密通信端口。
     */
    private Boolean starttlsEnable;

    /**
     * 使用 SSL 安全连接
     */
    private Boolean sslEnable;

    /**
     * SMTP 超时时长，单位毫秒，缺省值不超时
     */
    private Long timeout;

    /**
     * Socket 连接超时值，单位毫秒，缺省值不超时
     */
    private Long connectionTimeout;
}
