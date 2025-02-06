package com.ncobase.workflow.domain.bo;

import com.ncobase.framework.core.validate.AddGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * 驳回参数请求
 *
 * @author may
 */
@Data
public class BackProcessBo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务 ID
     */
    @NotNull(message = "任务 ID 不能为空", groups = AddGroup.class)
    private Long taskId;

    /**
     * 附件 id
     */
    private String fileId;

    /**
     * 消息类型
     */
    private List<String> messageType;

    /**
     * 驳回的节点 id(目前未使用，直接驳回到申请人)
     */
    @NotBlank(message = "驳回的节点不能为空", groups = AddGroup.class)
    private String nodeCode;

    /**
     * 办理意见
     */
    private String message;

    /**
     * 通知
     */
    private String notice;

    /**
     * 流程变量
     */
    private Map<String, Object> variables;

    public Map<String, Object> getVariables() {
        if (variables == null) {
            return new HashMap<>(16);
        }
        variables.entrySet().removeIf(entry -> Objects.isNull(entry.getValue()));
        return variables;
    }
}
