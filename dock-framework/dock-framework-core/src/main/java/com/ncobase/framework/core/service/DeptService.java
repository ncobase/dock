package com.ncobase.framework.core.service;

import com.ncobase.framework.core.domain.dto.DeptDTO;

import java.util.List;

/**
 * 通用 部门服务
 *
 * @author Lion Li
 */
public interface DeptService {

    /**
     * 通过部门 ID 查询部门名称
     *
     * @param deptIds 部门 ID 串逗号分隔
     * @return 部门名称串逗号分隔
     */
    String selectDeptNameByIds(String deptIds);

    /**
     * 根据部门 ID 查询部门负责人
     *
     * @param deptId 部门 ID，用于指定需要查询的部门
     * @return 返回该部门的负责人 ID
     */
    Long selectDeptLeaderById(Long deptId);

    /**
     * 查询部门
     *
     * @return 部门列表
     */
    List<DeptDTO> selectDeptsByList();

}
