package com.ncobase.generator.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import com.ncobase.framework.core.domain.R;
import com.ncobase.generator.domain.GeneratorTable;
import com.ncobase.generator.domain.GeneratorTableColumn;
import com.ncobase.generator.service.IGeneratorTableService;
import com.ncobase.framework.logger.annotation.Log;
import com.ncobase.framework.logger.enums.BusinessType;
import com.ncobase.framework.mybatis.core.page.PageQuery;
import com.ncobase.framework.mybatis.core.page.TableDataInfo;
import com.ncobase.framework.mybatis.helper.DataBaseHelper;
import com.ncobase.framework.web.web.core.BaseController;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成 操作处理
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/tool/generator")
public class GeneratorController extends BaseController {

    private final IGeneratorTableService generatorTableService;

    /**
     * 查询代码生成列表
     */
    @SaCheckPermission("tool:generator:list")
    @GetMapping("/list")
    public TableDataInfo<GeneratorTable> genList(GeneratorTable generatorTable, PageQuery pageQuery) {
        return generatorTableService.selectPageGeneratorTableList(generatorTable, pageQuery);
    }

    /**
     * 修改代码生成业务
     *
     * @param tableId 表 ID
     */
    @SaCheckPermission("tool:generator:query")
    @GetMapping(value = "/{tableId}")
    public R<Map<String, Object>> getInfo(@PathVariable Long tableId) {
        GeneratorTable table = generatorTableService.selectGeneratorTableById(tableId);
        List<GeneratorTable> tables = generatorTableService.selectGeneratorTableAll();
        List<GeneratorTableColumn> list = generatorTableService.selectGeneratorTableColumnListByTableId(tableId);
        Map<String, Object> map = new HashMap<>(3);
        map.put("info", table);
        map.put("rows", list);
        map.put("tables", tables);
        return R.ok(map);
    }

    /**
     * 查询数据库列表
     */
    @SaCheckPermission("tool:generator:list")
    @GetMapping("/db/list")
    public TableDataInfo<GeneratorTable> dataList(GeneratorTable generatorTable, PageQuery pageQuery) {
        return generatorTableService.selectPageDbTableList(generatorTable, pageQuery);
    }

    /**
     * 查询数据表字段列表
     *
     * @param tableId 表 ID
     */
    @SaCheckPermission("tool:generator:list")
    @GetMapping(value = "/column/{tableId}")
    public TableDataInfo<GeneratorTableColumn> columnList(@PathVariable("tableId") Long tableId) {
        TableDataInfo<GeneratorTableColumn> dataInfo = new TableDataInfo<>();
        List<GeneratorTableColumn> list = generatorTableService.selectGeneratorTableColumnListByTableId(tableId);
        dataInfo.setRows(list);
        dataInfo.setTotal(list.size());
        return dataInfo;
    }

    /**
     * 导入表结构（保存）
     *
     * @param tables 表名串
     */
    @SaCheckPermission("tool:generator:import")
    @Log(title = "代码生成", businessType = BusinessType.IMPORT)
    @PostMapping("/importTable")
    public R<Void> importTableSave(String tables, String dataName) {
        String[] tableNames = Convert.toStrArray(tables);
        // 查询表信息
        List<GeneratorTable> tableList = generatorTableService.selectDbTableListByNames(tableNames, dataName);
        generatorTableService.importGeneratorTable(tableList, dataName);
        return R.ok();
    }

    /**
     * 修改保存代码生成业务
     */
    @SaCheckPermission("tool:generator:edit")
    @Log(title = "代码生成", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> editSave(@Validated @RequestBody GeneratorTable generatorTable) {
        generatorTableService.validateEdit(generatorTable);
        generatorTableService.updateGeneratorTable(generatorTable);
        return R.ok();
    }

    /**
     * 删除代码生成
     *
     * @param tableIds 表 ID 串
     */
    @SaCheckPermission("tool:generator:remove")
    @Log(title = "代码生成", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tableIds}")
    public R<Void> remove(@PathVariable Long[] tableIds) {
        generatorTableService.deleteGeneratorTableByIds(tableIds);
        return R.ok();
    }

    /**
     * 预览代码
     *
     * @param tableId 表 ID
     */
    @SaCheckPermission("tool:generator:preview")
    @GetMapping("/preview/{tableId}")
    public R<Map<String, String>> preview(@PathVariable("tableId") Long tableId) throws IOException {
        Map<String, String> dataMap = generatorTableService.previewCode(tableId);
        return R.ok(dataMap);
    }

    /**
     * 生成代码（下载方式）
     *
     * @param tableId 表 ID
     */
    @SaCheckPermission("tool:generator:code")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/download/{tableId}")
    public void download(HttpServletResponse response, @PathVariable("tableId") Long tableId) throws IOException {
        byte[] data = generatorTableService.downloadCode(tableId);
        genCode(response, data);
    }

    /**
     * 生成代码（自定义路径）
     *
     * @param tableId 表 ID
     */
    @SaCheckPermission("tool:generator:code")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/genCode/{tableId}")
    public R<Void> genCode(@PathVariable("tableId") Long tableId) {
        generatorTableService.generatorCode(tableId);
        return R.ok();
    }

    /**
     * 同步数据库
     *
     * @param tableId 表 ID
     */
    @SaCheckPermission("tool:generator:edit")
    @Log(title = "代码生成", businessType = BusinessType.UPDATE)
    @GetMapping("/synchDb/{tableId}")
    public R<Void> synchDb(@PathVariable("tableId") Long tableId) {
        generatorTableService.synchDb(tableId);
        return R.ok();
    }

    /**
     * 批量生成代码
     *
     * @param tableIdStr 表 ID 串
     */
    @SaCheckPermission("tool:generator:code")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/batchGenCode")
    public void batchGenCode(HttpServletResponse response, String tableIdStr) throws IOException {
        String[] tableIds = Convert.toStrArray(tableIdStr);
        byte[] data = generatorTableService.downloadCode(tableIds);
        genCode(response, data);
    }

    /**
     * 生成 zip 文件
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException {
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=\"dock.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IoUtil.write(response.getOutputStream(), false, data);
    }

    /**
     * 查询数据源名称列表
     */
    @SaCheckPermission("tool:generator:list")
    @GetMapping(value = "/getDataNames")
    public R<Object> getCurrentDataSourceNameList() {
        return R.ok(DataBaseHelper.getDataSourceNameList());
    }
}
