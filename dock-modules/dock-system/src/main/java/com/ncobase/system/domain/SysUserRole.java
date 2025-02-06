package com.ncobase.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户和角色关联 sys_user_role
 *
 * @author Lion Li
 */

@Data
@TableName("sys_user_role")
public class SysUserRole {

    /**
     * 用户 ID
     */
    @TableId(type = IdType.INPUT)
    private Long userId;

    /**
     * 角色 ID
     */
    private Long roleId;

}
