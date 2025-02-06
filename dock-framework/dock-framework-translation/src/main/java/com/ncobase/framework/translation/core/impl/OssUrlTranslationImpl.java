package com.ncobase.framework.translation.core.impl;

import com.ncobase.framework.core.service.OssService;
import com.ncobase.framework.translation.annotation.TranslationType;
import com.ncobase.framework.translation.constant.TransConstant;
import com.ncobase.framework.translation.core.TranslationInterface;
import lombok.AllArgsConstructor;

/**
 * OSS 翻译实现
 *
 * @author Lion Li
 */
@AllArgsConstructor
@TranslationType(type = TransConstant.OSS_ID_TO_URL)
public class OssUrlTranslationImpl implements TranslationInterface<String> {

    private final OssService ossService;

    @Override
    public String translation(Object key, String other) {
        if (key instanceof String ids) {
            return ossService.selectUrlByIds(ids);
        } else if (key instanceof Long id) {
            return ossService.selectUrlByIds(id.toString());
        }
        return null;
    }
}
