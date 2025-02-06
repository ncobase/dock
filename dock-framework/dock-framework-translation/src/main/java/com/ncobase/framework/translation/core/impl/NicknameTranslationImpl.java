package com.ncobase.framework.translation.core.impl;

import com.ncobase.framework.core.service.UserService;
import com.ncobase.framework.translation.annotation.TranslationType;
import com.ncobase.framework.translation.constant.TransConstant;
import com.ncobase.framework.translation.core.TranslationInterface;
import lombok.AllArgsConstructor;

/**
 * 用户名称翻译实现
 *
 * @author may
 */
@AllArgsConstructor
@TranslationType(type = TransConstant.USER_ID_TO_NICKNAME)
public class NicknameTranslationImpl implements TranslationInterface<String> {

    private final UserService userService;

    @Override
    public String translation(Object key, String other) {
        if (key instanceof Long id) {
            return userService.selectNicknameByIds(id.toString());
        } else if (key instanceof String ids) {
            return userService.selectNicknameByIds(ids);
        }
        return null;
    }
}
