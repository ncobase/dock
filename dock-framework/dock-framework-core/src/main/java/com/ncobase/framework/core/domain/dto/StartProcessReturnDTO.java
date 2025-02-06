package com.ncobase.framework.core.domain.dto;


import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 启动流程返回对象
 *
 * @author Lion Li
 */
@Data
public class StartProcessReturnDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 流程实例 id
     */
    private Long processInstanceId;

    /**
     * 任务 id
     */
    private Long taskId;

}
