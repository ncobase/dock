package com.ncobase.workflow.handler;

import com.ncobase.framework.core.domain.event.ProcessDeleteEvent;
import com.ncobase.framework.core.domain.event.ProcessEvent;
import com.ncobase.framework.core.domain.event.ProcessTaskEvent;
import com.ncobase.framework.core.utils.SpringUtils;
import com.ncobase.framework.tenant.helper.TenantHelper;
import com.ncobase.workflow.common.ConditionalOnEnable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 流程监听服务
 *
 * @author may
 * @date 2024-06-02
 */
@ConditionalOnEnable
@Slf4j
@Component
public class FlowProcessEventHandler {

    /**
     * 总体流程监听 (例如：草稿，撤销，退回，作废，终止，已完成等)
     *
     * @param flowCode   流程定义编码
     * @param businessId 业务 id
     * @param status     状态
     * @param submit     当为 true 时为申请人节点办理
     */
    public void processHandler(String flowCode, String businessId, String status, Map<String, Object> params, boolean submit) {
        String tenantId = TenantHelper.getTenantId();
        log.info("发布流程事件，租户 ID: {}, 流程状态：{}, 流程编码：{}, 业务 ID: {}, 是否申请人节点办理：{}", tenantId, status, flowCode, businessId, submit);
        ProcessEvent processEvent = new ProcessEvent();
        processEvent.setTenantId(tenantId);
        processEvent.setFlowCode(flowCode);
        processEvent.setBusinessId(businessId);
        processEvent.setStatus(status);
        processEvent.setParams(params);
        processEvent.setSubmit(submit);
        SpringUtils.context().publishEvent(processEvent);
    }

    /**
     * 执行办理任务监听
     *
     * @param flowCode   流程定义编码
     * @param nodeCode   审批节点编码
     * @param taskId     任务 id
     * @param businessId 业务 id
     */
    public void processTaskHandler(String flowCode, String nodeCode, Long taskId, String businessId) {
        String tenantId = TenantHelper.getTenantId();
        log.info("发布流程任务事件，租户 ID: {}, 流程编码：{}, 节点编码：{}, 任务 ID: {}, 业务 ID: {}", tenantId, flowCode, nodeCode, taskId, businessId);
        ProcessTaskEvent processTaskEvent = new ProcessTaskEvent();
        processTaskEvent.setTenantId(tenantId);
        processTaskEvent.setFlowCode(flowCode);
        processTaskEvent.setNodeCode(nodeCode);
        processTaskEvent.setTaskId(taskId);
        processTaskEvent.setBusinessId(businessId);
        SpringUtils.context().publishEvent(processTaskEvent);
    }

    /**
     * 删除流程监听
     *
     * @param flowCode   流程定义编码
     * @param businessId 业务 ID
     */
    public void processDeleteHandler(String flowCode, String businessId) {
        String tenantId = TenantHelper.getTenantId();
        log.info("发布删除流程事件，租户 ID: {}, 流程编码：{}, 业务 ID: {}", tenantId, flowCode, businessId);
        ProcessDeleteEvent processDeleteEvent = new ProcessDeleteEvent();
        processDeleteEvent.setTenantId(tenantId);
        processDeleteEvent.setFlowCode(flowCode);
        processDeleteEvent.setBusinessId(businessId);
        SpringUtils.context().publishEvent(processDeleteEvent);
    }

}
