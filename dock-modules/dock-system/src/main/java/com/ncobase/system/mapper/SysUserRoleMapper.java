package com.ncobase.system.mapper;

import com.ncobase.framework.mybatis.core.mapper.BaseMapperPlus;
import com.ncobase.system.domain.SysUserRole;

import java.util.List;

/**
 * 用户与角色关联表 数据层
 *
 * @author Lion Li
 */
public interface SysUserRoleMapper extends BaseMapperPlus<SysUserRole, SysUserRole> {

    /**
     * 根据角色 ID 查询关联的用户 ID 列表
     *
     * @param roleId 角色 ID
     * @return 关联到指定角色的用户 ID 列表
     */
    List<Long> selectUserIdsByRoleId(Long roleId);

}
