package com.ncobase.demo.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ncobase.demo.domain.TestDemo;
import com.ncobase.framework.excel.annotation.ExcelNotation;
import com.ncobase.framework.excel.annotation.ExcelRequired;
import com.ncobase.framework.translation.annotation.Translation;
import com.ncobase.framework.translation.constant.TransConstant;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 测试单表视图对象 test_demo
 *
 * @author Lion Li
 * @date 2021-07-26
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TestDemo.class)
public class TestDemoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 部门 id
     */
    @ExcelRequired
    @ExcelProperty(value = "部门 id")
    private Long deptId;

    /**
     * 用户 id
     */
    @ExcelRequired
    @ExcelProperty(value = "用户 id")
    private Long userId;

    /**
     * 排序号
     */
    @ExcelRequired
    @ExcelProperty(value = "排序号")
    private Integer orderNum;

    /**
     * key 键
     */
    @ExcelNotation(value = "测试 key")
    @ExcelProperty(value = "key 键")
    private String testKey;

    /**
     * 值
     */
    @ExcelNotation(value = "测试 value")
    @ExcelProperty(value = "值")
    private String value;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 创建人
     */
    @ExcelProperty(value = "创建人")
    private Long createBy;

    /**
     * 创建人账号
     */
    @Translation(type = TransConstant.USER_ID_TO_NAME, mapper = "createBy")
    @ExcelProperty(value = "创建人账号")
    private String createByName;

    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 更新人
     */
    @ExcelProperty(value = "更新人")
    private Long updateBy;

    /**
     * 更新人账号
     */
    @Translation(type = TransConstant.USER_ID_TO_NAME, mapper = "updateBy")
    @ExcelProperty(value = "更新人账号")
    private String updateByName;

}
