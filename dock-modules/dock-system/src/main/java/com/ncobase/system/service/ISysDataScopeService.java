package com.ncobase.system.service;

/**
 * 通用 数据权限 服务
 *
 * @author Lion Li
 */
public interface ISysDataScopeService {

    /**
     * 获取角色自定义权限
     *
     * @param roleId 角色 id
     * @return 部门 id 组
     */
    String getRoleCustom(Long roleId);

    /**
     * 获取部门及以下权限
     *
     * @param deptId 部门 id
     * @return 部门 id 组
     */
    String getDeptAndChild(Long deptId);

}
