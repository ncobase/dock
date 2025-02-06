package com.ncobase.framework.core.domain.event;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * 总体流程监听
 *
 * @author may
 */
@Data
public class ProcessEvent implements Serializable {

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
     * 业务 id
     */
    private String businessId;

    /**
     * 状态
     */
    private String status;

    /**
     * 办理参数
     */
    private Map<String, Object> params;

    /**
     * 当为 true 时为申请人节点办理
     */
    private boolean submit;

}
