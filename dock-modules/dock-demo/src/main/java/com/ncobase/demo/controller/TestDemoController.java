package com.ncobase.demo.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ncobase.framework.core.domain.R;
import com.ncobase.framework.core.utils.MapstructUtils;
import com.ncobase.framework.core.utils.ValidatorUtils;
import com.ncobase.framework.core.validate.AddGroup;
import com.ncobase.framework.core.validate.EditGroup;
import com.ncobase.framework.core.validate.QueryGroup;
import com.ncobase.demo.domain.TestDemo;
import com.ncobase.demo.domain.bo.TestDemoBo;
import com.ncobase.demo.domain.bo.TestDemoImportVo;
import com.ncobase.demo.domain.vo.TestDemoVo;
import com.ncobase.demo.service.ITestDemoService;
import com.ncobase.framework.excel.core.ExcelResult;
import com.ncobase.framework.excel.utils.ExcelUtil;
import com.ncobase.framework.idempotent.annotation.RepeatSubmit;
import com.ncobase.framework.logger.annotation.Log;
import com.ncobase.framework.logger.enums.BusinessType;
import com.ncobase.framework.mybatis.core.page.PageQuery;
import com.ncobase.framework.mybatis.core.page.TableDataInfo;
import com.ncobase.framework.web.web.core.BaseController;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 测试单表 Controller
 *
 * @author Lion Li
 * @date 2021-07-26
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/demo/demo")
public class TestDemoController extends BaseController {

    private final ITestDemoService testDemoService;

    /**
     * 查询测试单表列表
     */
    @SaCheckPermission("demo:demo:list")
    @GetMapping("/list")
    public TableDataInfo<TestDemoVo> list(@Validated(QueryGroup.class) TestDemoBo bo, PageQuery pageQuery) {
        return testDemoService.queryPageList(bo, pageQuery);
    }

    /**
     * 自定义分页查询
     */
    @SaCheckPermission("demo:demo:list")
    @GetMapping("/page")
    public TableDataInfo<TestDemoVo> page(@Validated(QueryGroup.class) TestDemoBo bo, PageQuery pageQuery) {
        return testDemoService.customPageList(bo, pageQuery);
    }

    /**
     * 导入数据
     *
     * @param file 导入文件
     */
    @Log(title = "测试单表", businessType = BusinessType.IMPORT)
    @SaCheckPermission("demo:demo:import")
    @PostMapping(value = "/importData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<Void> importData(@RequestPart("file") MultipartFile file) throws Exception {
        ExcelResult<TestDemoImportVo> excelResult = ExcelUtil.importExcel(file.getInputStream(), TestDemoImportVo.class, true);
        List<TestDemo> list = MapstructUtils.convert(excelResult.getList(), TestDemo.class);
        testDemoService.saveBatch(list);
        return R.ok(excelResult.getAnalysis());
    }

    /**
     * 导出测试单表列表
     */
    @SaCheckPermission("demo:demo:export")
    @Log(title = "测试单表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@Validated TestDemoBo bo, HttpServletResponse response) {
        List<TestDemoVo> list = testDemoService.queryList(bo);
        // 测试雪花 id 导出
        //        for (TestDemoVo vo : list) {
        //            vo.setId(1234567891234567893L);
        //        }
        ExcelUtil.exportExcel(list, "测试单表", TestDemoVo.class, response);
    }

    /**
     * 获取测试单表详细信息
     *
     * @param id 测试 ID
     */
    @SaCheckPermission("demo:demo:query")
    @GetMapping("/{id}")
    public R<TestDemoVo> getInfo(@NotNull(message = "主键不能为空")
                                 @PathVariable("id") Long id) {
        return R.ok(testDemoService.queryById(id));
    }

    /**
     * 新增测试单表
     */
    @SaCheckPermission("demo:demo:add")
    @Log(title = "测试单表", businessType = BusinessType.INSERT)
    @RepeatSubmit(interval = 2, timeUnit = TimeUnit.SECONDS, message = "{repeat.submit.message}")
    @PostMapping()
    public R<Void> add(@RequestBody TestDemoBo bo) {
        // 使用校验工具对标 @Validated(AddGroup.class) 注解
        // 用于在非 Controller 的地方校验对象
        ValidatorUtils.validate(bo, AddGroup.class);
        return toAjax(testDemoService.insertByBo(bo));
    }

    /**
     * 修改测试单表
     */
    @SaCheckPermission("demo:demo:edit")
    @Log(title = "测试单表", businessType = BusinessType.UPDATE)
    @RepeatSubmit
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TestDemoBo bo) {
        return toAjax(testDemoService.updateByBo(bo));
    }

    /**
     * 删除测试单表
     *
     * @param ids 测试 ID 串
     */
    @SaCheckPermission("demo:demo:remove")
    @Log(title = "测试单表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(testDemoService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
