package com.ncobase.system.domain.bo;

import com.ncobase.framework.core.validate.AddGroup;
import com.ncobase.framework.core.validate.EditGroup;
import com.ncobase.framework.mybatis.core.domain.BaseEntity;
import com.ncobase.system.domain.SysClient;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 授权管理业务对象 sys_client
 *
 * @author Michelle.Chung
 * @date 2023-05-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysClient.class, reverseConvertGenerate = false)
public class SysClientBo extends BaseEntity {

    /**
     * id
     */
    @NotNull(message = "id 不能为空", groups = {EditGroup.class})
    private Long id;

    /**
     * 客户端 id
     */
    private String clientId;

    /**
     * 客户端 key
     */
    @NotBlank(message = "客户端 key 不能为空", groups = {AddGroup.class, EditGroup.class})
    private String clientKey;

    /**
     * 客户端秘钥
     */
    @NotBlank(message = "客户端秘钥不能为空", groups = {AddGroup.class, EditGroup.class})
    private String clientSecret;

    /**
     * 授权类型
     */
    @NotNull(message = "授权类型不能为空", groups = {AddGroup.class, EditGroup.class})
    private List<String> grantTypeList;

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


}
