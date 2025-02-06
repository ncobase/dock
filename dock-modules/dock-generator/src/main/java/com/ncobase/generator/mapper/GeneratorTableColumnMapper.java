package com.ncobase.generator.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.ncobase.generator.domain.GeneratorTableColumn;
import com.ncobase.framework.mybatis.core.mapper.BaseMapperPlus;

/**
 * 业务字段 数据层
 *
 * @author Lion Li
 */
@InterceptorIgnore(dataPermission = "true", tenantLine = "true")
public interface GeneratorTableColumnMapper extends BaseMapperPlus<GeneratorTableColumn, GeneratorTableColumn> {

}
