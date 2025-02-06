package com.ncobase.demo.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ncobase.framework.core.domain.R;
import com.ncobase.framework.core.validate.AddGroup;
import com.ncobase.framework.core.validate.EditGroup;
import com.ncobase.framework.core.validate.QueryGroup;
import com.ncobase.demo.domain.bo.TestTreeBo;
import com.ncobase.demo.domain.vo.TestTreeVo;
import com.ncobase.demo.service.ITestTreeService;
import com.ncobase.framework.excel.utils.ExcelUtil;
import com.ncobase.framework.idempotent.annotation.RepeatSubmit;
import com.ncobase.framework.logger.annotation.Log;
import com.ncobase.framework.logger.enums.BusinessType;
import com.ncobase.framework.web.web.core.BaseController;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 测试树表 Controller
 *
 * @author Lion Li
 * @date 2021-07-26
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/demo/tree")
public class TestTreeController extends BaseController {

    private final ITestTreeService testTreeService;

    /**
     * 查询测试树表列表
     */
    @SaCheckPermission("demo:tree:list")
    @GetMapping("/list")
    public R<List<TestTreeVo>> list(@Validated(QueryGroup.class) TestTreeBo bo) {
        List<TestTreeVo> list = testTreeService.queryList(bo);
        return R.ok(list);
    }

    /**
     * 导出测试树表列表
     */
    @SaCheckPermission("demo:tree:export")
    @Log(title = "测试树表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated TestTreeBo bo, HttpServletResponse response) {
        List<TestTreeVo> list = testTreeService.queryList(bo);
        ExcelUtil.exportExcel(list, "测试树表", TestTreeVo.class, response);
    }

    /**
     * 获取测试树表详细信息
     *
     * @param id 测试树 ID
     */
    @SaCheckPermission("demo:tree:query")
    @GetMapping("/{id}")
    public R<TestTreeVo> getInfo(@NotNull(message = "主键不能为空")
                                 @PathVariable("id") Long id) {
        return R.ok(testTreeService.queryById(id));
    }

    /**
     * 新增测试树表
     */
    @SaCheckPermission("demo:tree:add")
    @Log(title = "测试树表", businessType = BusinessType.INSERT)
    @RepeatSubmit
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TestTreeBo bo) {
        return toAjax(testTreeService.insertByBo(bo));
    }

    /**
     * 修改测试树表
     */
    @SaCheckPermission("demo:tree:edit")
    @Log(title = "测试树表", businessType = BusinessType.UPDATE)
    @RepeatSubmit
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TestTreeBo bo) {
        return toAjax(testTreeService.updateByBo(bo));
    }

    /**
     * 删除测试树表
     *
     * @param ids 测试树 ID 串
     */
    @SaCheckPermission("demo:tree:remove")
    @Log(title = "测试树表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(testTreeService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
