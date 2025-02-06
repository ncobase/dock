package com.ncobase.workflow.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.tree.Tree;
import com.ncobase.framework.core.domain.R;
import com.ncobase.framework.core.validate.AddGroup;
import com.ncobase.framework.core.validate.EditGroup;
import com.ncobase.framework.excel.utils.ExcelUtil;
import com.ncobase.framework.idempotent.annotation.RepeatSubmit;
import com.ncobase.framework.logger.annotation.Log;
import com.ncobase.framework.logger.enums.BusinessType;
import com.ncobase.framework.web.web.core.BaseController;
import com.ncobase.workflow.common.ConditionalOnEnable;
import com.ncobase.workflow.domain.bo.FlowCategoryBo;
import com.ncobase.workflow.domain.vo.FlowCategoryVo;
import com.ncobase.workflow.service.IFlwCategoryService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程分类
 *
 * @author may
 */
@ConditionalOnEnable
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/category")
public class FlwCategoryController extends BaseController {

    private final IFlwCategoryService flwCategoryService;

    /**
     * 查询流程分类列表
     */
    @SaCheckPermission("workflow:category:list")
    @GetMapping("/list")
    public R<List<FlowCategoryVo>> list(FlowCategoryBo bo) {
        List<FlowCategoryVo> list = flwCategoryService.queryList(bo);
        return R.ok(list);
    }

    /**
     * 导出流程分类列表
     */
    @SaCheckPermission("workflow:category:export")
    @Log(title = "流程分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FlowCategoryBo bo, HttpServletResponse response) {
        List<FlowCategoryVo> list = flwCategoryService.queryList(bo);
        ExcelUtil.exportExcel(list, "流程分类", FlowCategoryVo.class, response);
    }

    /**
     * 获取流程分类详细信息
     *
     * @param categoryId 主键
     */
    @SaCheckPermission("workflow:category:query")
    @GetMapping("/{categoryId}")
    public R<FlowCategoryVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long categoryId) {
        flwCategoryService.checkCategoryDataScope(categoryId);
        return R.ok(flwCategoryService.queryById(categoryId));
    }

    /**
     * 新增流程分类
     */
    @SaCheckPermission("workflow:category:add")
    @Log(title = "流程分类", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FlowCategoryBo category) {
        if (!flwCategoryService.checkCategoryNameUnique(category)) {
            return R.fail("新增流程分类'" + category.getCategoryName() + "'失败，流程分类名称已存在");
        }
        return toAjax(flwCategoryService.insertByBo(category));
    }

    /**
     * 修改流程分类
     */
    @SaCheckPermission("workflow:category:edit")
    @Log(title = "流程分类", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FlowCategoryBo category) {
        Long categoryId = category.getCategoryId();
        flwCategoryService.checkCategoryDataScope(categoryId);
        if (!flwCategoryService.checkCategoryNameUnique(category)) {
            return R.fail("修改流程分类'" + category.getCategoryName() + "'失败，流程分类名称已存在");
        } else if (category.getParentId().equals(categoryId)) {
            return R.fail("修改流程分类'" + category.getCategoryName() + "'失败，上级流程分类不能是自己");
        }
        return toAjax(flwCategoryService.updateByBo(category));
    }

    /**
     * 删除流程分类
     *
     * @param categoryId 主键
     */
    @SaCheckPermission("workflow:category:remove")
    @Log(title = "流程分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{categoryId}")
    public R<Void> remove(@PathVariable Long categoryId) {
        if (flwCategoryService.hasChildByCategoryId(categoryId)) {
            return R.warn("存在下级流程分类，不允许删除");
        }
        if (flwCategoryService.checkCategoryExistDefinition(categoryId)) {
            return R.warn("流程分类存在流程定义，不允许删除");
        }
        return toAjax(flwCategoryService.deleteWithValidById(categoryId));
    }

    /**
     * 获取流程分类树列表
     *
     * @param categoryBo 流程分类
     */
    @GetMapping("/categoryTree")
    public R<List<Tree<String>>> categoryTree(FlowCategoryBo categoryBo) {
        return R.ok(flwCategoryService.selectCategoryTreeList(categoryBo));
    }

}
