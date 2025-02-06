package com.ncobase.system.service;

import cn.hutool.core.lang.tree.Tree;
import com.ncobase.system.domain.SysMenu;
import com.ncobase.system.domain.bo.SysMenuBo;
import com.ncobase.system.domain.vo.RouterVo;
import com.ncobase.system.domain.vo.SysMenuVo;

import java.util.List;
import java.util.Set;

/**
 * 菜单 业务层
 *
 * @author Lion Li
 */
public interface ISysMenuService {

    /**
     * 根据用户查询系统菜单列表
     *
     * @param userId 用户 ID
     * @return 菜单列表
     */
    List<SysMenuVo> selectMenuList(Long userId);

    /**
     * 根据用户查询系统菜单列表
     *
     * @param menu   菜单信息
     * @param userId 用户 ID
     * @return 菜单列表
     */
    List<SysMenuVo> selectMenuList(SysMenuBo menu, Long userId);

    /**
     * 根据用户 ID 查询权限
     *
     * @param userId 用户 ID
     * @return 权限列表
     */
    Set<String> selectMenuPermsByUserId(Long userId);

    /**
     * 根据角色 ID 查询权限
     *
     * @param roleId 角色 ID
     * @return 权限列表
     */
    Set<String> selectMenuPermsByRoleId(Long roleId);

    /**
     * 根据用户 ID 查询菜单树信息
     *
     * @param userId 用户 ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenuTreeByUserId(Long userId);

    /**
     * 根据角色 ID 查询菜单树信息
     *
     * @param roleId 角色 ID
     * @return 选中菜单列表
     */
    List<Long> selectMenuListByRoleId(Long roleId);

    /**
     * 根据租户套餐 ID 查询菜单树信息
     *
     * @param packageId 租户套餐 ID
     * @return 选中菜单列表
     */
    List<Long> selectMenuListByPackageId(Long packageId);

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    List<RouterVo> buildMenus(List<SysMenu> menus);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    List<Tree<Long>> buildMenuTreeSelect(List<SysMenuVo> menus);

    /**
     * 根据菜单 ID 查询信息
     *
     * @param menuId 菜单 ID
     * @return 菜单信息
     */
    SysMenuVo selectMenuById(Long menuId);

    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单 ID
     * @return 结果 true 存在 false 不存在
     */
    boolean hasChildByMenuId(Long menuId);

    /**
     * 查询菜单是否存在角色
     *
     * @param menuId 菜单 ID
     * @return 结果 true 存在 false 不存在
     */
    boolean checkMenuExistRole(Long menuId);

    /**
     * 新增保存菜单信息
     *
     * @param bo 菜单信息
     * @return 结果
     */
    int insertMenu(SysMenuBo bo);

    /**
     * 修改保存菜单信息
     *
     * @param bo 菜单信息
     * @return 结果
     */
    int updateMenu(SysMenuBo bo);

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单 ID
     * @return 结果
     */
    int deleteMenuById(Long menuId);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    boolean checkMenuNameUnique(SysMenuBo menu);
}
