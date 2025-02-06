package com.ncobase.workflow.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ncobase.framework.mybatis.annotation.DataColumn;
import com.ncobase.framework.mybatis.annotation.DataPermission;
import com.ncobase.framework.mybatis.core.mapper.BaseMapperPlus;
import com.ncobase.framework.mybatis.helper.DataBaseHelper;
import com.ncobase.workflow.domain.FlowCategory;
import com.ncobase.workflow.domain.vo.FlowCategoryVo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 流程分类 Mapper 接口
 *
 * @author may
 * @date 2023-06-27
 */
public interface FlwCategoryMapper extends BaseMapperPlus<FlowCategory, FlowCategoryVo> {

    /**
     * 统计指定流程分类 ID 的分类数量
     *
     * @param categoryId 流程分类 ID
     * @return 该流程分类 ID 的分类数量
     */
    @DataPermission({
        @DataColumn(key = "deptName", value = "createDept")
    })
    long countCategoryById(Long categoryId);

    /**
     * 根据父流程分类 ID 查询其所有子流程分类的列表
     *
     * @param parentId 父流程分类 ID
     * @return 包含子流程分类的列表
     */
    default List<FlowCategory> selectListByParentId(Long parentId) {
        return this.selectList(new LambdaQueryWrapper<FlowCategory>()
            .select(FlowCategory::getCategoryId)
            .apply(DataBaseHelper.findInSet(parentId, "ancestors")));
    }

    /**
     * 根据父流程分类 ID 查询包括父 ID 及其所有子流程分类 ID 的列表
     *
     * @param parentId 父流程分类 ID
     * @return 包含父 ID 和子流程分类 ID 的列表
     */
    default List<Long> selectCategoryIdsByParentId(Long parentId) {
        return Stream.concat(
            this.selectListByParentId(parentId).stream()
                .map(FlowCategory::getCategoryId),
            Stream.of(parentId)
        ).collect(Collectors.toList());
    }

}
