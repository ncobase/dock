package com.ncobase.workflow.domain.bo;

import com.ncobase.framework.core.validate.AddGroup;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 终止任务请求对象
 *
 * @author may
 */
@Data
public class FlowTerminationBo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务 id
     */
    @NotNull(message = "任务 id 为空", groups = AddGroup.class)
    private Long taskId;

    /**
     * 审批意见
     */
    private String comment;
}
