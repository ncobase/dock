package com.ncobase.framework.core.domain.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 抄送
 *
 * @author may
 */
@Data
public class FlowCopyDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

}
