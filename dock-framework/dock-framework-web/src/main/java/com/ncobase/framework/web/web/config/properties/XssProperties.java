package com.ncobase.framework.web.web.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * xss 过滤 配置属性
 *
 * @author Lion Li
 */
@Data
@ConfigurationProperties(prefix = "xss")
public class XssProperties {

    /**
     * Xss 开关
     */
    private Boolean enabled;

    /**
     * 排除路径
     */
    private List<String> excludeUrls = new ArrayList<>();

}
