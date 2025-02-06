package com.ncobase.demo.controller;

import cn.hutool.core.collection.CollUtil;
import com.ncobase.demo.domain.vo.ExportDemoVo;
import com.ncobase.demo.listener.ExportDemoListener;
import com.ncobase.demo.service.IExportExcelService;
import com.ncobase.framework.excel.core.ExcelResult;
import com.ncobase.framework.excel.utils.ExcelUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试 Excel 功能
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/demo/excel")
public class TestExcelController {

    private final IExportExcelService exportExcelService;

    /**
     * 单列表多数据
     */
    @GetMapping("/exportTemplateOne")
    public void exportTemplateOne(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();
        map.put("title", "单列表多数据");
        map.put("test1", "数据测试 1");
        map.put("test2", "数据测试 2");
        map.put("test3", "数据测试 3");
        map.put("test4", "数据测试 4");
        map.put("testTest", "666");
        List<TestObj> list = new ArrayList<>();
        list.add(new TestObj("单列表测试 1", "列表测试 1", "列表测试 2", "列表测试 3", "列表测试 4"));
        list.add(new TestObj("单列表测试 2", "列表测试 5", "列表测试 6", "列表测试 7", "列表测试 8"));
        list.add(new TestObj("单列表测试 3", "列表测试 9", "列表测试 10", "列表测试 11", "列表测试 12"));
        ExcelUtil.exportTemplate(CollUtil.newArrayList(map, list), "单列表.xlsx", "excel/单列表.xlsx", response);
    }

    /**
     * 多列表多数据
     */
    @GetMapping("/exportTemplateMuliti")
    public void exportTemplateMuliti(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();
        map.put("title1", "标题 1");
        map.put("title2", "标题 2");
        map.put("title3", "标题 3");
        map.put("title4", "标题 4");
        map.put("author", "Ncobase Authors");
        List<TestObj1> list1 = new ArrayList<>();
        list1.add(new TestObj1("list1 测试 1", "list1 测试 2", "list1 测试 3"));
        list1.add(new TestObj1("list1 测试 4", "list1 测试 5", "list1 测试 6"));
        list1.add(new TestObj1("list1 测试 7", "list1 测试 8", "list1 测试 9"));
        List<TestObj1> list2 = new ArrayList<>();
        list2.add(new TestObj1("list2 测试 1", "list2 测试 2", "list2 测试 3"));
        list2.add(new TestObj1("list2 测试 4", "list2 测试 5", "list2 测试 6"));
        List<TestObj1> list3 = new ArrayList<>();
        list3.add(new TestObj1("list3 测试 1", "list3 测试 2", "list3 测试 3"));
        List<TestObj1> list4 = new ArrayList<>();
        list4.add(new TestObj1("list4 测试 1", "list4 测试 2", "list4 测试 3"));
        list4.add(new TestObj1("list4 测试 4", "list4 测试 5", "list4 测试 6"));
        list4.add(new TestObj1("list4 测试 7", "list4 测试 8", "list4 测试 9"));
        list4.add(new TestObj1("list4 测试 10", "list4 测试 11", "list4 测试 12"));
        Map<String, Object> multiListMap = new HashMap<>();
        multiListMap.put("map", map);
        multiListMap.put("data1", list1);
        multiListMap.put("data2", list2);
        multiListMap.put("data3", list3);
        multiListMap.put("data4", list4);
        ExcelUtil.exportTemplateMultiList(multiListMap, "多列表.xlsx", "excel/多列表.xlsx", response);
    }

    /**
     * 导出下拉框
     *
     * @param response /
     */
    @GetMapping("/exportWithOptions")
    public void exportWithOptions(HttpServletResponse response) {
        exportExcelService.exportWithOptions(response);
    }

    /**
     * 多个 sheet 导出
     */
    @GetMapping("/exportTemplateMultiSheet")
    public void exportTemplateMultiSheet(HttpServletResponse response) {
        List<TestObj1> list1 = new ArrayList<>();
        list1.add(new TestObj1("list1 测试 1", "list1 测试 2", "list1 测试 3"));
        list1.add(new TestObj1("list1 测试 4", "list1 测试 5", "list1 测试 6"));
        List<TestObj1> list2 = new ArrayList<>();
        list2.add(new TestObj1("list2 测试 1", "list2 测试 2", "list2 测试 3"));
        list2.add(new TestObj1("list2 测试 4", "list2 测试 5", "list2 测试 6"));
        List<TestObj1> list3 = new ArrayList<>();
        list3.add(new TestObj1("list3 测试 1", "list3 测试 2", "list3 测试 3"));
        list3.add(new TestObj1("list3 测试 4", "list3 测试 5", "list3 测试 6"));
        List<TestObj1> list4 = new ArrayList<>();
        list4.add(new TestObj1("list4 测试 1", "list4 测试 2", "list4 测试 3"));
        list4.add(new TestObj1("list4 测试 4", "list4 测试 5", "list4 测试 6"));

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> sheetMap1 = new HashMap<>();
        sheetMap1.put("data1", list1);
        Map<String, Object> sheetMap2 = new HashMap<>();
        sheetMap2.put("data2", list2);
        Map<String, Object> sheetMap3 = new HashMap<>();
        sheetMap3.put("data3", list3);
        Map<String, Object> sheetMap4 = new HashMap<>();
        sheetMap4.put("data4", list4);

        list.add(sheetMap1);
        list.add(sheetMap2);
        list.add(sheetMap3);
        list.add(sheetMap4);
        ExcelUtil.exportTemplateMultiSheet(list, "多 sheet 列表", "excel/多 sheet 列表.xlsx", response);
    }

    /**
     * 导入表格
     */
    @PostMapping(value = "/importWithOptions", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<ExportDemoVo> importWithOptions(@RequestPart("file") MultipartFile file) throws Exception {
        // 处理解析结果
        ExcelResult<ExportDemoVo> excelResult = ExcelUtil.importExcel(file.getInputStream(), ExportDemoVo.class, new ExportDemoListener());
        return excelResult.getList();
    }

    @Data
    @AllArgsConstructor
    static class TestObj1 {
        private String test1;
        private String test2;
        private String test3;
    }

    @Data
    @AllArgsConstructor
    static class TestObj {
        private String name;
        private String list1;
        private String list2;
        private String list3;
        private String list4;
    }

}
