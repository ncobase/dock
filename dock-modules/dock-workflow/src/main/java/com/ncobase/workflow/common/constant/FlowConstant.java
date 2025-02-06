package com.ncobase.workflow.common.constant;


/**
 * 工作流常量
 *
 * @author may
 */
public interface FlowConstant {

    /**
     * 流程发起人
     */
    String INITIATOR = "initiator";

    /**
     * 流程实例 id
     */
    String PROCESS_INSTANCE_ID = "processInstanceId";

    /**
     * 业务 id
     */
    String BUSINESS_ID = "businessId";

    /**
     * 任务 id
     */
    String TASK_ID = "taskId";

    /**
     * 委托
     */
    String DELEGATE_TASK = "delegateTask";

    /**
     * 转办
     */
    String TRANSFER_TASK = "transferTask";

    /**
     * 加签
     */
    String ADD_SIGNATURE = "addSignature";

    /**
     * 减签
     */
    String REDUCTION_SIGNATURE = "reductionSignature";

    /**
     * 流程分类 Id 转名称
     */
    String CATEGORY_ID_TO_NAME = "category_id_to_name";

    /**
     * 流程分类名称
     */
    String FLOW_CATEGORY_NAME = "flow_category_name#30d";

    /**
     * 默认租户 OA 申请分类 id
     */
    Long FLOW_CATEGORY_ID = 100L;

}
