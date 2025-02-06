package com.ncobase.framework.core.service;

import com.ncobase.framework.core.domain.dto.CompleteTaskDTO;
import com.ncobase.framework.core.domain.dto.StartProcessDTO;
import com.ncobase.framework.core.domain.dto.StartProcessReturnDTO;

import java.util.List;
import java.util.Map;

/**
 * 通用 工作流服务
 *
 * @author may
 */
public interface WorkflowService {

    /**
     * 运行中的实例 删除程实例，删除历史记录，删除业务与流程关联信息
     *
     * @param businessIds 业务 id
     * @return 结果
     */
    boolean deleteInstance(List<Long> businessIds);

    /**
     * 获取当前流程状态
     *
     * @param taskId 任务 id
     * @return 状态
     */
    String getBusinessStatusByTaskId(Long taskId);

    /**
     * 获取当前流程状态
     *
     * @param businessId 业务 id
     * @return 状态
     */
    String getBusinessStatus(String businessId);

    /**
     * 设置流程变量
     *
     * @param instanceId 流程实例 id
     * @param variable   流程变量
     */
    void setVariable(Long instanceId, Map<String, Object> variable);

    /**
     * 获取流程变量
     *
     * @param instanceId 流程实例 id
     */
    Map<String, Object> instanceVariable(Long instanceId);

    /**
     * 按照业务 id 查询流程实例 id
     *
     * @param businessId 业务 id
     * @return 结果
     */
    Long getInstanceIdByBusinessId(String businessId);

    /**
     * 新增租户流程定义
     *
     * @param tenantId 租户 id
     */
    void syncDef(String tenantId);

    /**
     * 启动流程
     *
     * @param startProcess 参数
     * @return 结果
     */
    StartProcessReturnDTO startWorkFlow(StartProcessDTO startProcess);

    /**
     * 办理任务
     *
     * @param completeTask 参数
     * @return 结果
     */
    boolean completeTask(CompleteTaskDTO completeTask);
}
