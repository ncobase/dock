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
public class SocialLoginBody extends LoginBody {

    /**
     * 第三方登录平台
     */
    @NotBlank(message = "{social.source.not.blank}")
    private String source;

    /**
     * 第三方登录 code
     */
    @NotBlank(message = "{social.code.not.blank}")
    private String socialCode;

    /**
     * 第三方登录 socialState
     */
    @NotBlank(message = "{social.state.not.blank}")
    private String socialState;

}
