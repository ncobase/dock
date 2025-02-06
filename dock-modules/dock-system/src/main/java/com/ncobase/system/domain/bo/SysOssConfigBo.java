package com.ncobase.system.domain.bo;

import com.ncobase.framework.core.validate.AddGroup;
import com.ncobase.framework.core.validate.EditGroup;
import com.ncobase.framework.mybatis.core.domain.BaseEntity;
import com.ncobase.system.domain.SysOssConfig;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 对象存储配置业务对象 sys_oss_config
 *
 * @author Lion Li
 * @author 孤舟烟雨
 * @date 2021-08-13
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysOssConfig.class, reverseConvertGenerate = false)
public class SysOssConfigBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = {EditGroup.class})
    private Long ossConfigId;

    /**
     * 配置 key
     */
    @NotBlank(message = "配置 key 不能为空", groups = {AddGroup.class, EditGroup.class})
    @Size(min = 2, max = 100, message = "configKey 长度必须介于{min}和{max} 之间")
    private String configKey;

    /**
     * accessKey
     */
    @NotBlank(message = "accessKey 不能为空", groups = {AddGroup.class, EditGroup.class})
    @Size(min = 2, max = 100, message = "accessKey 长度必须介于{min}和{max} 之间")
    private String accessKey;

    /**
     * 秘钥
     */
    @NotBlank(message = "secretKey 不能为空", groups = {AddGroup.class, EditGroup.class})
    @Size(min = 2, max = 100, message = "secretKey 长度必须介于{min}和{max} 之间")
    private String secretKey;

    /**
     * 桶名称
     */
    @NotBlank(message = "桶名称不能为空", groups = {AddGroup.class, EditGroup.class})
    @Size(min = 2, max = 100, message = "bucketName 长度必须介于{min}和{max}之间")
    private String bucketName;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 访问站点
     */
    @NotBlank(message = "访问站点不能为空", groups = {AddGroup.class, EditGroup.class})
    @Size(min = 2, max = 100, message = "endpoint 长度必须介于{min}和{max}之间")
    private String endpoint;

    /**
     * 自定义域名
     */
    private String domain;

    /**
     * 是否 https（Y=是，N=否）
     */
    private String isHttps;

    /**
     * 是否默认（0=是，1=否）
     */
    private String status;

    /**
     * 域
     */
    private String region;

    /**
     * 扩展字段
     */
    private String ext1;

    /**
     * 备注
     */
    private String remark;

    /**
     * 桶权限类型 (0private 1public 2custom)
     */
    @NotBlank(message = "桶权限类型不能为空", groups = {AddGroup.class, EditGroup.class})
    private String accessPolicy;

}
