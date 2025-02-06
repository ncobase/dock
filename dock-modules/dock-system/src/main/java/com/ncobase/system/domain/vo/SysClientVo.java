package com.ncobase.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ncobase.framework.excel.annotation.ExcelDictFormat;
import com.ncobase.framework.excel.convert.ExcelDictConvert;
import com.ncobase.system.domain.SysClient;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


/**
 * 授权管理视图对象 sys_client
 *
 * @author Michelle.Chung
 * @date 2023-05-15
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysClient.class)
public class SysClientVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ExcelProperty(value = "id")
    private Long id;

    /**
     * 客户端 id
     */
    @ExcelProperty(value = "客户端 id")
    private String clientId;

    /**
     * 客户端 key
     */
    @ExcelProperty(value = "客户端 key")
    private String clientKey;

    /**
     * 客户端秘钥
     */
    @ExcelProperty(value = "客户端秘钥")
    private String clientSecret;

    /**
     * 授权类型
     */
    @ExcelProperty(value = "授权类型")
    private List<String> grantTypeList;

    /**
     * 授权类型
     */
    private String grantType;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * token 活跃超时时间
     */
    @ExcelProperty(value = "token 活跃超时时间")
    private Long activeTimeout;

    /**
     * token 固定超时时间
     */
    @ExcelProperty(value = "token 固定超时时间")
    private Long timeout;

    /**
     * 状态（0 正常 1 停用）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=正常，1=停用")
    private String status;


}
