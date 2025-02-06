package com.ncobase.workflow.domain.bo;

import com.ncobase.framework.core.validate.AddGroup;
import com.ncobase.framework.core.validate.EditGroup;
import com.ncobase.framework.mybatis.core.domain.BaseEntity;
import com.ncobase.workflow.domain.FlowCategory;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程分类业务对象 wf_category
 *
 * @author may
 * @date 2023-06-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FlowCategory.class, reverseConvertGenerate = false)
public class FlowCategoryBo extends BaseEntity {

    /**
     * 流程分类 ID
     */
    @NotNull(message = "流程分类 ID 不能为空", groups = {EditGroup.class})
    private Long categoryId;

    /**
     * 父流程分类 id
     */
    @NotNull(message = "父流程分类 id 不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long parentId;

    /**
     * 流程分类名称
     */
    @NotBlank(message = "流程分类名称不能为空", groups = {AddGroup.class, EditGroup.class})
    private String categoryName;

    /**
     * 显示顺序
     */
    private Long orderNum;

}
