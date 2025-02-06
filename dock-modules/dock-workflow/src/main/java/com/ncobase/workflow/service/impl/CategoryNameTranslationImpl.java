package com.ncobase.workflow.service.impl;

import cn.hutool.core.convert.Convert;
import com.ncobase.framework.translation.annotation.TranslationType;
import com.ncobase.framework.translation.core.TranslationInterface;
import com.ncobase.workflow.common.ConditionalOnEnable;
import com.ncobase.workflow.common.constant.FlowConstant;
import com.ncobase.workflow.service.IFlwCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 流程分类名称翻译实现
 *
 * @author AprilWind
 */
@ConditionalOnEnable
@Slf4j
@RequiredArgsConstructor
@Service
@TranslationType(type = FlowConstant.CATEGORY_ID_TO_NAME)
public class CategoryNameTranslationImpl implements TranslationInterface<String> {

    private final IFlwCategoryService flwCategoryService;

    @Override
    public String translation(Object key, String other) {
        Long id = null;
        if (key instanceof String categoryId) {
            id = Convert.toLong(categoryId);
        } else if (key instanceof Long categoryId) {
            id = categoryId;
        }
        return flwCategoryService.selectCategoryNameById(id);
    }
}
