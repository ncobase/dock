package com.ncobase.workflow.domain.bo;

import com.ncobase.framework.core.validate.AddGroup;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 撤销任务请求对象
 *
 * @author may
 */
@Data
public class FlowCancelBo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务 ID
     */
    @NotBlank(message = "业务 ID 不能为空", groups = AddGroup.class)
    private String businessId;

    /**
     * 办理意见
     */
    private String message;
}
