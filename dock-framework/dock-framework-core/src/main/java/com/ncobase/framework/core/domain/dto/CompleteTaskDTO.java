package com.ncobase.framework.core.domain.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 办理任务请求对象
 *
 * @author may
 */
@Data
public class CompleteTaskDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务 id
     */
    private Long taskId;

    /**
     * 附件 id
     */
    private String fileId;

    /**
     * 抄送人员
     */
    private List<FlowCopyDTO> flowCopyList;

    /**
     * 消息类型
     */
    private List<String> messageType;

    /**
     * 办理意见
     */
    private String message;

    /**
     * 消息通知
     */
    private String notice;

    /**
     * 流程变量
     */
    private Map<String, Object> variables;

    /**
     * 扩展变量 (此处为逗号分隔的 ossId)
     */
    private String ext;

    public Map<String, Object> getVariables() {
        if (variables == null) {
            return new HashMap<>(16);
        }
        variables.entrySet().removeIf(entry -> Objects.isNull(entry.getValue()));
        return variables;
    }

}
