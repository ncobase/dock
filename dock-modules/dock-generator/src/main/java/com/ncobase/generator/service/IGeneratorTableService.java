package com.ncobase.generator.service;

import com.ncobase.generator.domain.GeneratorTable;
import com.ncobase.generator.domain.GeneratorTableColumn;
import com.ncobase.framework.mybatis.core.page.PageQuery;
import com.ncobase.framework.mybatis.core.page.TableDataInfo;

import java.util.List;
import java.util.Map;

/**
 * 业务 服务层
 *
 * @author Lion Li
 */
public interface IGeneratorTableService {

    /**
     * 查询业务字段列表
     *
     * @param tableId 业务字段编号
     * @return 业务字段集合
     */
    List<GeneratorTableColumn> selectGeneratorTableColumnListByTableId(Long tableId);

    /**
     * 查询业务列表
     *
     * @param generatorTable 业务信息
     * @return 业务集合
     */
    TableDataInfo<GeneratorTable> selectPageGeneratorTableList(GeneratorTable generatorTable, PageQuery pageQuery);

    /**
     * 查询据库列表
     *
     * @param generatorTable 业务信息
     * @return 数据库表集合
     */
    TableDataInfo<GeneratorTable> selectPageDbTableList(GeneratorTable generatorTable, PageQuery pageQuery);

    /**
     * 查询据库列表
     *
     * @param tableNames 表名称组
     * @param dataName   数据源名称
     * @return 数据库表集合
     */
    List<GeneratorTable> selectDbTableListByNames(String[] tableNames, String dataName);

    /**
     * 查询所有表信息
     *
     * @return 表信息集合
     */
    List<GeneratorTable> selectGeneratorTableAll();

    /**
     * 查询业务信息
     *
     * @param id 业务 ID
     * @return 业务信息
     */
    GeneratorTable selectGeneratorTableById(Long id);

    /**
     * 修改业务
     *
     * @param generatorTable 业务信息
     */
    void updateGeneratorTable(GeneratorTable generatorTable);

    /**
     * 删除业务信息
     *
     * @param tableIds 需要删除的表数据 ID
     */
    void deleteGeneratorTableByIds(Long[] tableIds);

    /**
     * 导入表结构
     *
     * @param tableList 导入表列表
     * @param dataName  数据源名称
     */
    void importGeneratorTable(List<GeneratorTable> tableList, String dataName);

    /**
     * 根据表名称查询列信息
     *
     * @param tableName 表名称
     * @param dataName  数据源名称
     * @return 列信息
     */
    List<GeneratorTableColumn> selectDbTableColumnsByName(String tableName, String dataName);

    /**
     * 预览代码
     *
     * @param tableId 表编号
     * @return 预览数据列表
     */
    Map<String, String> previewCode(Long tableId);

    /**
     * 生成代码（下载方式）
     *
     * @param tableId 表名称
     * @return 数据
     */
    byte[] downloadCode(Long tableId);

    /**
     * 生成代码（自定义路径）
     *
     * @param tableId 表名称
     */
    void generatorCode(Long tableId);

    /**
     * 同步数据库
     *
     * @param tableId 表名称
     */
    void synchDb(Long tableId);

    /**
     * 批量生成代码（下载方式）
     *
     * @param tableIds 表 ID 数组
     * @return 数据
     */
    byte[] downloadCode(String[] tableIds);

    /**
     * 修改保存参数校验
     *
     * @param generatorTable 业务信息
     */
    void validateEdit(GeneratorTable generatorTable);
}
