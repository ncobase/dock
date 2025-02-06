package com.ncobase.framework.core.service;

import com.ncobase.framework.core.domain.dto.UserDTO;

import java.util.List;

/**
 * 通用 用户服务
 *
 * @author Lion Li
 */
public interface UserService {

    /**
     * 通过用户 ID 查询用户账户
     *
     * @param userId 用户 ID
     * @return 用户账户
     */
    String selectUserNameById(Long userId);

    /**
     * 通过用户 ID 查询用户账户
     *
     * @param userId 用户 ID
     * @return 用户名称
     */
    String selectNicknameById(Long userId);

    /**
     * 通过用户 ID 查询用户账户
     *
     * @param userIds 用户 ID 多个用逗号隔开
     * @return 用户名称
     */
    String selectNicknameByIds(String userIds);

    /**
     * 通过用户 ID 查询用户手机号
     *
     * @param userId 用户 id
     * @return 用户手机号
     */
    String selectPhonenumberById(Long userId);

    /**
     * 通过用户 ID 查询用户邮箱
     *
     * @param userId 用户 id
     * @return 用户邮箱
     */
    String selectEmailById(Long userId);

    /**
     * 通过用户 ID 查询用户列表
     *
     * @param userIds 用户 ids
     * @return 用户列表
     */
    List<UserDTO> selectListByIds(List<Long> userIds);

    /**
     * 通过角色 ID 查询用户 ID
     *
     * @param roleIds 角色 ids
     * @return 用户 ids
     */
    List<Long> selectUserIdsByRoleIds(List<Long> roleIds);

    /**
     * 通过角色 ID 查询用户
     *
     * @param roleIds 角色 ids
     * @return 用户
     */
    List<UserDTO> selectUsersByRoleIds(List<Long> roleIds);

    /**
     * 通过部门 ID 查询用户
     *
     * @param deptIds 部门 ids
     * @return 用户
     */
    List<UserDTO> selectUsersByDeptIds(List<Long> deptIds);

    /**
     * 通过岗位 ID 查询用户
     *
     * @param postIds 岗位 ids
     * @return 用户
     */
    List<UserDTO> selectUsersByPostIds(List<Long> postIds);

}
