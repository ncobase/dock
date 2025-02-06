package com.ncobase.framework.core.domain.event;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 删除流程监听
 *
 * @author AprilWind
 */
@Data
public class ProcessDeleteEvent implements Serializable {

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

}
