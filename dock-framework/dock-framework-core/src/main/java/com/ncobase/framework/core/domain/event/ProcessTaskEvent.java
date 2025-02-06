package com.ncobase.framework.core.domain.event;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 流程办理监听
 *
 * @author may
 */
@Data
public class ProcessTaskEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 租户 ID
     */
    private String tenantId;

    /**
     * 流程定义编码
     */
    private String flowCode;

    /**
     * 审批节点编码
     */
    private String nodeCode;

    /**
     * 任务 id
     */
    private Long taskId;

    /**
     * 业务 id
     */
    private String businessId;

}
