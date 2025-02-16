package com.ncobase.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ncobase.framework.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 租户套餐对象 sys_tenant_package
 *
 * @author Michelle.Chung
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_tenant_package")
public class SysTenantPackage extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 租户套餐 id
     */
    @TableId(value = "package_id")
    private Long packageId;

    /**
     * 套餐名称
     */
    private String packageName;

    /**
     * 关联菜单 id
     */
    private String menuIds;

    /**
     * 备注
     */
    private String remark;

    /**
     * 菜单树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示）
     */
    private Boolean menuCheckStrictly;

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
