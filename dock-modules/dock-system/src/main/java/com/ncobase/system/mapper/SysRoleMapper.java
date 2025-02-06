package com.ncobase.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ncobase.framework.mybatis.annotation.DataColumn;
import com.ncobase.framework.mybatis.annotation.DataPermission;
import com.ncobase.framework.mybatis.core.mapper.BaseMapperPlus;
import com.ncobase.system.domain.SysRole;
import com.ncobase.system.domain.vo.SysRoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色表 数据层
 *
 * @author Lion Li
 */
public interface SysRoleMapper extends BaseMapperPlus<SysRole, SysRoleVo> {

    /**
     * 分页查询角色列表
     *
     * @param page         分页对象
     * @param queryWrapper 查询条件
     * @return 包含角色信息的分页结果
     */
    @DataPermission({
        @DataColumn(key = "deptName", value = "d.dept_id"),
        @DataColumn(key = "userName", value = "r.create_by")
    })
    Page<SysRoleVo> selectPageRoleList(@Param("page") Page<SysRole> page, @Param(Constants.WRAPPER) Wrapper<SysRole> queryWrapper);

    /**
     * 根据条件分页查询角色数据
     *
     * @param queryWrapper 查询条件
     * @return 角色数据集合信息
     */
    @DataPermission({
        @DataColumn(key = "deptName", value = "d.dept_id"),
        @DataColumn(key = "userName", value = "r.create_by")
    })
    List<SysRoleVo> selectRoleList(@Param(Constants.WRAPPER) Wrapper<SysRole> queryWrapper);

    /**
     * 根据角色 ID 查询角色信息
     *
     * @param roleId 角色 ID
     * @return 对应的角色信息
     */
    @DataPermission({
        @DataColumn(key = "deptName", value = "d.dept_id"),
        @DataColumn(key = "userName", value = "r.create_by")
    })
    SysRoleVo selectRoleById(Long roleId);

    /**
     * 根据用户 ID 查询角色
     *
     * @param userId 用户 ID
     * @return 角色列表
     */
    List<SysRoleVo> selectRolePermissionByUserId(Long userId);

    /**
     * 根据用户 ID 查询角色
     *
     * @param userId 用户 ID
     * @return 角色列表
     */
    List<SysRoleVo> selectRolesByUserId(Long userId);

}
