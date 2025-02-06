package com.ncobase.demo.service;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 导出下拉框 Excel 示例
 *
 * @author Emil.Zhang
 */
public interface IExportExcelService {

    /**
     * 导出下拉框
     *
     * @param response /
     */
    void exportWithOptions(HttpServletResponse response);
}
