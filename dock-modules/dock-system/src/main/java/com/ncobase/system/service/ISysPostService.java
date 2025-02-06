package com.ncobase.system.service;

import com.ncobase.framework.mybatis.core.page.PageQuery;
import com.ncobase.framework.mybatis.core.page.TableDataInfo;
import com.ncobase.system.domain.bo.SysPostBo;
import com.ncobase.system.domain.vo.SysPostVo;

import java.util.List;

/**
 * 岗位信息 服务层
 *
 * @author Lion Li
 */
public interface ISysPostService {


    TableDataInfo<SysPostVo> selectPagePostList(SysPostBo post, PageQuery pageQuery);

    /**
     * 查询岗位信息集合
     *
     * @param post 岗位信息
     * @return 岗位列表
     */
    List<SysPostVo> selectPostList(SysPostBo post);

    /**
     * 查询用户所属岗位组
     *
     * @param userId 用户 ID
     * @return 岗位 ID
     */
    List<SysPostVo> selectPostsByUserId(Long userId);

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    List<SysPostVo> selectPostAll();

    /**
     * 通过岗位 ID 查询岗位信息
     *
     * @param postId 岗位 ID
     * @return 角色对象信息
     */
    SysPostVo selectPostById(Long postId);

    /**
     * 根据用户 ID 获取岗位选择框列表
     *
     * @param userId 用户 ID
     * @return 选中岗位 ID 列表
     */
    List<Long> selectPostListByUserId(Long userId);

    /**
     * 通过岗位 ID 串查询岗位
     *
     * @param postIds 岗位 id 串
     * @return 岗位列表信息
     */
    List<SysPostVo> selectPostByIds(List<Long> postIds);

    /**
     * 校验岗位名称
     *
     * @param post 岗位信息
     * @return 结果
     */
    boolean checkPostNameUnique(SysPostBo post);

    /**
     * 校验岗位编码
     *
     * @param post 岗位信息
     * @return 结果
     */
    boolean checkPostCodeUnique(SysPostBo post);

    /**
     * 通过岗位 ID 查询岗位使用数量
     *
     * @param postId 岗位 ID
     * @return 结果
     */
    long countUserPostById(Long postId);

    /**
     * 通过部门 ID 查询岗位使用数量
     *
     * @param deptId 部门 id
     * @return 结果
     */
    long countPostByDeptId(Long deptId);

    /**
     * 删除岗位信息
     *
     * @param postId 岗位 ID
     * @return 结果
     */
    int deletePostById(Long postId);

    /**
     * 批量删除岗位信息
     *
     * @param postIds 需要删除的岗位 ID
     * @return 结果
     */
    int deletePostByIds(Long[] postIds);

    /**
     * 新增保存岗位信息
     *
     * @param bo 岗位信息
     * @return 结果
     */
    int insertPost(SysPostBo bo);

    /**
     * 修改保存岗位信息
     *
     * @param bo 岗位信息
     * @return 结果
     */
    int updatePost(SysPostBo bo);
}
