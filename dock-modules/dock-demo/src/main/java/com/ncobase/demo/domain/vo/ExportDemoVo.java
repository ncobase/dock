package com.ncobase.demo.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ncobase.framework.core.enums.UserStatus;
import com.ncobase.framework.core.validate.AddGroup;
import com.ncobase.framework.core.validate.EditGroup;
import com.ncobase.framework.excel.annotation.ExcelDictFormat;
import com.ncobase.framework.excel.annotation.ExcelEnumFormat;
import com.ncobase.framework.excel.convert.ExcelDictConvert;
import com.ncobase.framework.excel.convert.ExcelEnumConvert;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 带有下拉选的 Excel 导出
 *
 * @author Emil.Zhang
 */
@Data
@ExcelIgnoreUnannotated
@AllArgsConstructor
@NoArgsConstructor
public class ExportDemoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户昵称
     */
    @ExcelProperty(value = "用户名", index = 0)
    @NotEmpty(message = "用户名不能为空", groups = AddGroup.class)
    private String nickName;

    /**
     * 用户类型
     * </p>
     * 使用 ExcelEnumFormat 注解需要进行下拉选的部分
     */
    @ExcelProperty(value = "用户类型", index = 1, converter = ExcelEnumConvert.class)
    @ExcelEnumFormat(enumClass = UserStatus.class, textField = "info")
    @NotEmpty(message = "用户类型不能为空", groups = AddGroup.class)
    private String userStatus;

    /**
     * 性别
     * <p>
     * 使用 ExcelDictFormat 注解需要进行下拉选的部分
     */
    @ExcelProperty(value = "性别", index = 2, converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_user_sex")
    @NotEmpty(message = "性别不能为空", groups = AddGroup.class)
    private String gender;

    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号", index = 3)
    @NotEmpty(message = "手机号不能为空", groups = AddGroup.class)
    private String phoneNumber;

    /**
     * Email
     */
    @ExcelProperty(value = "Email", index = 4)
    @NotEmpty(message = "Email 不能为空", groups = AddGroup.class)
    private String email;

    /**
     * 省
     * <p>
     * 级联下拉，仅判断是否选了
     */
    @ExcelProperty(value = "省", index = 5)
    @NotNull(message = "省不能为空", groups = AddGroup.class)
    private String province;

    /**
     * 数据库中的省 ID
     * </p>
     * 处理完毕后再判断是否市正确的值
     */
    @NotNull(message = "请勿手动输入", groups = EditGroup.class)
    private Integer provinceId;

    /**
     * 市
     * <p>
     * 级联下拉
     */
    @ExcelProperty(value = "市", index = 6)
    @NotNull(message = "市不能为空", groups = AddGroup.class)
    private String city;

    /**
     * 数据库中的市 ID
     */
    @NotNull(message = "请勿手动输入", groups = EditGroup.class)
    private Integer cityId;

    /**
     * 县
     * <p>
     * 级联下拉
     */
    @ExcelProperty(value = "县", index = 7)
    @NotNull(message = "县不能为空", groups = AddGroup.class)
    private String area;

    /**
     * 数据库中的县 ID
     */
    @NotNull(message = "请勿手动输入", groups = EditGroup.class)
    private Integer areaId;
}
