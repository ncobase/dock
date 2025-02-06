package com.ncobase.workflow.domain.bo;

import com.ncobase.framework.core.validate.AddGroup;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 作废请求对象
 *
 * @author may
 */
@Data
public class FlowInvalidBo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 流程实例 id
     */
    @NotNull(message = "流程实例 id 为空", groups = AddGroup.class)
    private Long id;

    /**
     * 审批意见
     */
    private String comment;
}
