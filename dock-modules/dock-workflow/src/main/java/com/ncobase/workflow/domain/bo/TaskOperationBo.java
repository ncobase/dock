package com.ncobase.workflow.domain.bo;

import com.ncobase.framework.core.validate.AddGroup;
import com.ncobase.framework.core.validate.EditGroup;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


/**
 * 任务操作业务对象，用于描述任务委派、转办、加签等操作的必要参数
 * 包含了用户 ID、任务 ID、任务相关的消息、以及加签/减签的用户 ID
 *
 * @author AprilWind
 */
@Data
public class TaskOperationBo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 委派/转办人的用户 ID（必填，准对委派/转办人操作）
     */
    @NotNull(message = "委派/转办人 id 不能为空", groups = {AddGroup.class})
    private String userId;

    /**
     * 加签/减签人的用户 ID 列表（必填，针对加签/减签操作）
     */
    @NotNull(message = "加签/减签 id 不能为空", groups = {EditGroup.class})
    private List<String> userIds;

    /**
     * 任务 ID（必填）
     */
    @NotNull(message = "任务 id 不能为空")
    private Long taskId;

    /**
     * 意见或备注信息（可选）
     */
    private String message;

}
