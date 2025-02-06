package com.ncobase.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ncobase.framework.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 授权管理对象 sys_client
 *
 * @author Michelle.Chung
 * @date 2023-05-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_client")
public class SysClient extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 客户端 id
     */
    private String clientId;

    /**
     * 客户端 key
     */
    private String clientKey;

    /**
     * 客户端秘钥
     */
    private String clientSecret;

    /**
     * 授权类型
     */
    private String grantType;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * token 活跃超时时间
     */
    private Long activeTimeout;

    /**
     * token 固定超时时间
     */
    private Long timeout;

    /**
     * 状态（0 正常 1 停用）
     */
    private String status;

    /**
     * 删除标志（0 代表存在 1 代表删除）
     */
    @TableLogic
    private String delFlag;


}
