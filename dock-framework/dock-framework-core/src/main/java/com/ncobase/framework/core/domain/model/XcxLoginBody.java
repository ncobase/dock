package com.ncobase.framework.core.domain.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 三方登录对象
 *
 * @author Lion Li
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class XcxLoginBody extends LoginBody {

    /**
     * 小程序 id(多个小程序时使用)
     */
    private String appid;

    /**
     * 小程序 code
     */
    @NotBlank(message = "{xcx.code.not.blank}")
    private String xcxCode;

}
