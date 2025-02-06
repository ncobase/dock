package com.ncobase.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ncobase.framework.core.constant.CacheNames;
import com.ncobase.framework.core.utils.StreamUtils;
import com.ncobase.system.domain.SysDept;
import com.ncobase.system.domain.SysRoleDept;
import com.ncobase.system.mapper.SysDeptMapper;
import com.ncobase.system.mapper.SysRoleDeptMapper;
import com.ncobase.system.service.ISysDataScopeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据权限 实现
 * <p>
 * 注意：此 Service 内不允许调用标注 `数据权限` 注解的方法
 * 例如：deptMapper.selectList 此 selectList 方法标注了 `数据权限` 注解 会出现循环解析的问题
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Service("sdss")
public class SysDataScopeServiceImpl implements ISysDataScopeService {

    private final SysRoleDeptMapper roleDeptMapper;
    private final SysDeptMapper deptMapper;

    /**
     * 获取角色自定义权限
     *
     * @param roleId 角色 Id
     * @return 部门 Id 组
     */
    @Cacheable(cacheNames = CacheNames.SYS_ROLE_CUSTOM, key = "#roleId", condition = "#roleId != null")
    @Override
    public String getRoleCustom(Long roleId) {
        if (ObjectUtil.isNull(roleId)) {
            return "-1";
        }
        List<SysRoleDept> list = roleDeptMapper.selectList(
            new LambdaQueryWrapper<SysRoleDept>()
                .select(SysRoleDept::getDeptId)
                .eq(SysRoleDept::getRoleId, roleId));
        if (CollUtil.isNotEmpty(list)) {
            return StreamUtils.join(list, rd -> Convert.toStr(rd.getDeptId()));
        }
        return "-1";
    }

    /**
     * 获取部门及以下权限
     *
     * @param deptId 部门 Id
     * @return 部门 Id 组
     */
    @Cacheable(cacheNames = CacheNames.SYS_DEPT_AND_CHILD, key = "#deptId", condition = "#deptId != null")
    @Override
    public String getDeptAndChild(Long deptId) {
        if (ObjectUtil.isNull(deptId)) {
            return "-1";
        }
        List<SysDept> deptList = deptMapper.selectListByParentId(deptId);
        List<Long> ids = StreamUtils.toList(deptList, SysDept::getDeptId);
        ids.add(deptId);
        if (CollUtil.isNotEmpty(ids)) {
            return StreamUtils.join(ids, Convert::toStr);
        }
        return "-1";
    }

}
