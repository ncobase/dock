package com.ncobase.generator.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.ncobase.generator.domain.GeneratorTable;
import com.ncobase.framework.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 业务 数据层
 *
 * @author Lion Li
 */
@InterceptorIgnore(dataPermission = "true", tenantLine = "true")
public interface GeneratorTableMapper extends BaseMapperPlus<GeneratorTable, GeneratorTable> {

    /**
     * 查询所有表信息
     *
     * @return 表信息集合
     */
    List<GeneratorTable> selectGeneratorTableAll();

    /**
     * 查询表 ID 业务信息
     *
     * @param id 业务 ID
     * @return 业务信息
     */
    GeneratorTable selectGeneratorTableById(Long id);

    /**
     * 查询表名称业务信息
     *
     * @param tableName 表名称
     * @return 业务信息
     */
    GeneratorTable selectGeneratorTableByName(String tableName);

    /**
     * 查询指定数据源下的所有表名列表
     *
     * @param dataName 数据源名称，用于选择不同的数据源
     * @return 当前数据库中的表名列表
     * @DS("") 使用默认数据源执行查询操作
     */
    @DS("")
    List<String> selectTableNameList(String dataName);
}
