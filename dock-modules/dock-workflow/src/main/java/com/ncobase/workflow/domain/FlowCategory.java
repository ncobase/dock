package com.ncobase.workflow.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ncobase.framework.tenant.core.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 流程分类对象 wf_category
 *
 * @author may
 * @date 2023-06-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("flow_category")
public class FlowCategory extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 流程分类 ID
     */
    @TableId(value = "category_id")
    private Long categoryId;

    /**
     * 父流程分类 id
     */
    private Long parentId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 流程分类名称
     */
    private String categoryName;

    /**
     * 显示顺序
     */
    private Long orderNum;

    /**
     * 删除标志（0 代表存在 1 代表删除）
     */
    @TableLogic
    private String delFlag;

}
