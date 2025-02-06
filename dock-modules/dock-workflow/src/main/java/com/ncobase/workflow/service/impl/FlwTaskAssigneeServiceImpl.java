package com.ncobase.workflow.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.ncobase.framework.core.domain.dto.DeptDTO;
import com.ncobase.framework.core.domain.dto.TaskAssigneeDTO;
import com.ncobase.framework.core.domain.dto.UserDTO;
import com.ncobase.framework.core.domain.model.TaskAssigneeBody;
import com.ncobase.framework.core.enums.FormatsType;
import com.ncobase.framework.core.exception.ServiceException;
import com.ncobase.framework.core.service.DeptService;
import com.ncobase.framework.core.service.TaskAssigneeService;
import com.ncobase.framework.core.service.UserService;
import com.ncobase.framework.core.utils.DateUtils;
import com.ncobase.framework.core.utils.StringUtils;
import com.ncobase.workflow.common.ConditionalOnEnable;
import com.ncobase.workflow.common.enums.TaskAssigneeEnum;
import com.ncobase.workflow.service.IFlwTaskAssigneeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.ui.dto.HandlerFunDto;
import org.dromara.warm.flow.ui.dto.HandlerQuery;
import org.dromara.warm.flow.ui.dto.TreeFunDto;
import org.dromara.warm.flow.ui.service.HandlerSelectService;
import org.dromara.warm.flow.ui.vo.HandlerSelectVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 流程设计器 - 获取办理人权限设置列表
 *
 * @author AprilWind
 */
@ConditionalOnEnable
@Slf4j
@RequiredArgsConstructor
@Service
public class FlwTaskAssigneeServiceImpl implements IFlwTaskAssigneeService, HandlerSelectService {

    private static final String DEFAULT_GROUP_NAME = "默认分组";
    private final TaskAssigneeService taskAssigneeService;
    private final UserService userService;
    private final DeptService deptService;

    /**
     * 获取办理人权限设置列表 tabs 页签
     *
     * @return tabs 页签
     */
    @Override
    public List<String> getHandlerType() {
        return TaskAssigneeEnum.getAssigneeTypeList();
    }

    /**
     * 获取办理列表，同时构建左侧部门树状结构
     *
     * @param query 查询条件
     * @return HandlerSelectVo
     */
    @Override
    public HandlerSelectVo getHandlerSelect(HandlerQuery query) {
        // 获取任务办理类型
        TaskAssigneeEnum type = TaskAssigneeEnum.fromDesc(query.getHandlerType());
        // 转换查询条件为 TaskAssigneeBody
        TaskAssigneeBody taskQuery = BeanUtil.toBean(query, TaskAssigneeBody.class);

        // 统一查询并构建业务数据
        TaskAssigneeDTO dto = fetchTaskAssigneeData(type, taskQuery);
        List<DeptDTO> depts = fetchDeptData(type);

        return getHandlerSelectVo(buildHandlerData(dto, type), buildDeptTree(depts));
    }

    /**
     * 根据任务办理类型查询对应的数据
     */
    private TaskAssigneeDTO fetchTaskAssigneeData(TaskAssigneeEnum type, TaskAssigneeBody taskQuery) {
        return switch (type) {
            case USER -> taskAssigneeService.selectUsersByTaskAssigneeList(taskQuery);
            case ROLE -> taskAssigneeService.selectRolesByTaskAssigneeList(taskQuery);
            case DEPT -> taskAssigneeService.selectDeptsByTaskAssigneeList(taskQuery);
            case POST -> taskAssigneeService.selectPostsByTaskAssigneeList(taskQuery);
            default -> throw new ServiceException("Unsupported handler type");
        };
    }

    /**
     * 根据任务办理类型获取部门数据
     */
    private List<DeptDTO> fetchDeptData(TaskAssigneeEnum type) {
        if (type == TaskAssigneeEnum.USER || type == TaskAssigneeEnum.DEPT || type == TaskAssigneeEnum.POST) {
            return deptService.selectDeptsByList();
        }
        return new ArrayList<>();
    }

    /**
     * 构建部门树状结构
     */
    private TreeFunDto<DeptDTO> buildDeptTree(List<DeptDTO> depts) {
        return new TreeFunDto<>(depts)
            .setId(dept -> String.valueOf(dept.getDeptId()))
            .setName(DeptDTO::getDeptName)
            .setParentId(dept -> String.valueOf(dept.getParentId()));
    }

    /**
     * 构建任务办理人数据
     */
    private HandlerFunDto<TaskAssigneeDTO.TaskHandler> buildHandlerData(TaskAssigneeDTO dto, TaskAssigneeEnum type) {
        return new HandlerFunDto<>(dto.getList(), dto.getTotal())
            .setStorageId(assignee -> type.getCode() + assignee.getStorageId())
            .setHandlerCode(assignee -> StringUtils.blankToDefault(assignee.getHandlerCode(), "无"))
            .setHandlerName(assignee -> StringUtils.blankToDefault(assignee.getHandlerName(), "无"))
            .setGroupName(assignee -> StringUtils.defaultIfBlank(
                Optional.ofNullable(assignee.getGroupName())
                    .map(deptService::selectDeptNameByIds)
                    .orElse(DEFAULT_GROUP_NAME), DEFAULT_GROUP_NAME))
            .setCreateTime(assignee -> DateUtils.parseDateToStr(FormatsType.YYYY_MM_DD_HH_MM_SS, assignee.getCreateTime()));
    }

    /**
     * 根据存储标识符（storageId）解析分配类型和 ID，并获取对应的用户列表
     *
     * @param storageId 包含分配类型和 ID 的字符串（例如 "user:123" 或 "role:456"）
     * @return 与分配类型和 ID 匹配的用户列表，如果格式无效则返回空列表
     */
    @Override
    public List<UserDTO> fetchUsersByStorageId(String storageId) {
        List<UserDTO> list = new ArrayList<>();
        for (String str : storageId.split(StrUtil.COMMA)) {
            String[] parts = str.split(StrUtil.COLON, 2);
            if (parts.length < 2) {
                list.addAll(getUsersByType(TaskAssigneeEnum.USER, List.of(Long.valueOf(parts[0]))));
            } else {
                list.addAll(getUsersByType(TaskAssigneeEnum.fromCode(parts[0] + StrUtil.COLON), List.of(Long.valueOf(parts[1]))));
            }
        }
        return list;
    }

    /**
     * 根据指定的任务分配类型（TaskAssigneeEnum）和 ID 列表，获取对应的用户信息列表
     *
     * @param type 任务分配类型，表示用户、角色、部门或其他（TaskAssigneeEnum 枚举值）
     * @param ids  与指定分配类型关联的 ID 列表（例如用户 ID、角色 ID、部门 ID 等）
     * @return 返回包含用户信息的列表。如果类型为用户（USER），则通过用户 ID 列表查询；
     * 如果类型为角色（ROLE），则通过角色 ID 列表查询；
     * 如果类型为部门（DEPT），则通过部门 ID 列表查询；
     * 如果类型为岗位（POST）或无法识别的类型，则返回空列表
     */
    private List<UserDTO> getUsersByType(TaskAssigneeEnum type, List<Long> ids) {
        return switch (type) {
            case USER -> userService.selectListByIds(ids);
            case ROLE -> userService.selectUsersByRoleIds(ids);
            case DEPT -> userService.selectUsersByDeptIds(ids);
            case POST -> userService.selectUsersByPostIds(ids);
        };
    }

}
