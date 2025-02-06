package com.ncobase.workflow.domain.bo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 流程实例请求对象
 *
 * @author may
 */
@Data
public class FlowInstanceBo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 流程定义名称
     */
    private String flowName;

    /**
     * 流程定义编码
     */
    private String flowCode;

    /**
     * 任务发起人
     */
    private String startUserId;

    /**
     * 业务 id
     */
    private String businessId;

    /**
     * 流程分类 id
     */
    private String category;

    /**
     * 任务名称
     */
    private String nodeName;

    /**
     * 申请人 Ids
     */
    private List<Long> createByIds;

}
