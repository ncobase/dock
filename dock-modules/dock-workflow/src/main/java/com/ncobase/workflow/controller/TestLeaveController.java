package com.ncobase.workflow.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ncobase.framework.core.domain.R;
import com.ncobase.framework.core.validate.AddGroup;
import com.ncobase.framework.core.validate.EditGroup;
import com.ncobase.framework.excel.utils.ExcelUtil;
import com.ncobase.framework.idempotent.annotation.RepeatSubmit;
import com.ncobase.framework.logger.annotation.Log;
import com.ncobase.framework.logger.enums.BusinessType;
import com.ncobase.framework.mybatis.core.page.PageQuery;
import com.ncobase.framework.mybatis.core.page.TableDataInfo;
import com.ncobase.framework.web.web.core.BaseController;
import com.ncobase.workflow.common.ConditionalOnEnable;
import com.ncobase.workflow.domain.bo.TestLeaveBo;
import com.ncobase.workflow.domain.vo.TestLeaveVo;
import com.ncobase.workflow.service.ITestLeaveService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 请假
 *
 * @author may
 * @date 2023-07-21
 */
@ConditionalOnEnable
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/leave")
public class TestLeaveController extends BaseController {

    private final ITestLeaveService testLeaveService;

    /**
     * 查询请假列表
     */
    @SaCheckPermission("workflow:leave:list")
    @GetMapping("/list")
    public TableDataInfo<TestLeaveVo> list(TestLeaveBo bo, PageQuery pageQuery) {
        return testLeaveService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出请假列表
     */
    @SaCheckPermission("workflow:leave:export")
    @Log(title = "请假", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TestLeaveBo bo, HttpServletResponse response) {
        List<TestLeaveVo> list = testLeaveService.queryList(bo);
        ExcelUtil.exportExcel(list, "请假", TestLeaveVo.class, response);
    }

    /**
     * 获取请假详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("workflow:leave:query")
    @GetMapping("/{id}")
    public R<TestLeaveVo> getInfo(@NotNull(message = "主键不能为空")
                                  @PathVariable Long id) {
        return R.ok(testLeaveService.queryById(id));
    }

    /**
     * 新增请假
     */
    @SaCheckPermission("workflow:leave:add")
    @Log(title = "请假", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<TestLeaveVo> add(@Validated(AddGroup.class) @RequestBody TestLeaveBo bo) {
        return R.ok(testLeaveService.insertByBo(bo));
    }

    /**
     * 修改请假
     */
    @SaCheckPermission("workflow:leave:edit")
    @Log(title = "请假", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<TestLeaveVo> edit(@Validated(EditGroup.class) @RequestBody TestLeaveBo bo) {
        return R.ok(testLeaveService.updateByBo(bo));
    }

    /**
     * 删除请假
     *
     * @param ids 主键串
     */
    @SaCheckPermission("workflow:leave:remove")
    @Log(title = "请假", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(testLeaveService.deleteWithValidByIds(List.of(ids)));
    }
}
