package com.ncobase.generator.util;

import com.ncobase.framework.core.utils.StringUtils;
import com.ncobase.generator.config.GeneratorConfig;
import com.ncobase.generator.constant.GeneratorConstants;
import com.ncobase.generator.domain.GeneratorTable;
import com.ncobase.generator.domain.GeneratorTableColumn;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RegExUtils;

import java.util.Arrays;

/**
 * 代码生成器 工具类
 *
 * @author ruoyi
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeneratorUtils {

    /**
     * 初始化表信息
     */
    public static void initTable(GeneratorTable generatorTable) {
        generatorTable.setClassName(convertClassName(generatorTable.getTableName()));
        generatorTable.setPackageName(GeneratorConfig.getPackageName());
        generatorTable.setModuleName(getModuleName(GeneratorConfig.getPackageName()));
        generatorTable.setBusinessName(getBusinessName(generatorTable.getTableName()));
        generatorTable.setFunctionName(replaceText(generatorTable.getTableComment()));
        generatorTable.setFunctionAuthor(GeneratorConfig.getAuthor());
        generatorTable.setCreateTime(null);
        generatorTable.setUpdateTime(null);
    }

    /**
     * 初始化列属性字段
     */
    public static void initColumnField(GeneratorTableColumn column, GeneratorTable table) {
        String dataType = getDbType(column.getColumnType());
        // 统一转小写 避免有些数据库默认大写问题 如果需要特别书写方式 请在实体类增加注解标注别名
        String columnName = column.getColumnName().toLowerCase();
        column.setTableId(table.getTableId());
        column.setCreateTime(null);
        column.setUpdateTime(null);
        // 设置 java 字段名
        column.setJavaField(StringUtils.toCamelCase(columnName));
        // 设置默认类型
        column.setJavaType(GeneratorConstants.TYPE_STRING);
        column.setQueryType(GeneratorConstants.QUERY_EQ);

        if (arraysContains(GeneratorConstants.COLUMNTYPE_STR, dataType) || arraysContains(GeneratorConstants.COLUMNTYPE_TEXT, dataType)) {
            // 字符串长度超过 500 设置为文本域
            Integer columnLength = getColumnLength(column.getColumnType());
            String htmlType = columnLength >= 500 || arraysContains(GeneratorConstants.COLUMNTYPE_TEXT, dataType) ? GeneratorConstants.HTML_TEXTAREA : GeneratorConstants.HTML_INPUT;
            column.setHtmlType(htmlType);
        } else if (arraysContains(GeneratorConstants.COLUMNTYPE_TIME, dataType)) {
            column.setJavaType(GeneratorConstants.TYPE_DATE);
            column.setHtmlType(GeneratorConstants.HTML_DATETIME);
        } else if (arraysContains(GeneratorConstants.COLUMNTYPE_NUMBER, dataType)) {
            column.setHtmlType(GeneratorConstants.HTML_INPUT);
            // 数据库的数字字段与 java 不匹配 且很多数据库的数字字段很模糊 例如 oracle 只有 number 没有细分
            // 所以默认数字类型全为 Long 可在界面上自行编辑想要的类型 有什么特殊需求也可以在这里特殊处理
            column.setJavaType(GeneratorConstants.TYPE_LONG);
        }

        // BO 对象 默认插入勾选
        if (!arraysContains(GeneratorConstants.COLUMNNAME_NOT_ADD, columnName) && !column.isPk()) {
            column.setIsInsert(GeneratorConstants.REQUIRE);
        }
        // BO 对象 默认编辑勾选
        if (!arraysContains(GeneratorConstants.COLUMNNAME_NOT_EDIT, columnName)) {
            column.setIsEdit(GeneratorConstants.REQUIRE);
        }
        // VO 对象 默认返回勾选
        if (!arraysContains(GeneratorConstants.COLUMNNAME_NOT_LIST, columnName)) {
            column.setIsList(GeneratorConstants.REQUIRE);
        }
        // BO 对象 默认查询勾选
        if (!arraysContains(GeneratorConstants.COLUMNNAME_NOT_QUERY, columnName) && !column.isPk()) {
            column.setIsQuery(GeneratorConstants.REQUIRE);
        }

        // 查询字段类型
        if (StringUtils.endsWithIgnoreCase(columnName, "name")) {
            column.setQueryType(GeneratorConstants.QUERY_LIKE);
        }
        // 状态字段设置单选框
        if (StringUtils.endsWithIgnoreCase(columnName, "status")) {
            column.setHtmlType(GeneratorConstants.HTML_RADIO);
        }
        // 类型&性别字段设置下拉框
        else if (StringUtils.endsWithIgnoreCase(columnName, "type")
            || StringUtils.endsWithIgnoreCase(columnName, "sex")) {
            column.setHtmlType(GeneratorConstants.HTML_SELECT);
        }
        // 图片字段设置图片上传控件
        else if (StringUtils.endsWithIgnoreCase(columnName, "image")) {
            column.setHtmlType(GeneratorConstants.HTML_IMAGE_UPLOAD);
        }
        // 文件字段设置文件上传控件
        else if (StringUtils.endsWithIgnoreCase(columnName, "file")) {
            column.setHtmlType(GeneratorConstants.HTML_FILE_UPLOAD);
        }
        // 内容字段设置富文本控件
        else if (StringUtils.endsWithIgnoreCase(columnName, "content")) {
            column.setHtmlType(GeneratorConstants.HTML_EDITOR);
        }
    }

    /**
     * 校验数组是否包含指定值
     *
     * @param arr         数组
     * @param targetValue 值
     * @return 是否包含
     */
    public static boolean arraysContains(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

    /**
     * 获取模块名
     *
     * @param packageName 包名
     * @return 模块名
     */
    public static String getModuleName(String packageName) {
        int lastIndex = packageName.lastIndexOf(".");
        int nameLength = packageName.length();
        return StringUtils.substring(packageName, lastIndex + 1, nameLength);
    }

    /**
     * 获取业务名
     *
     * @param tableName 表名
     * @return 业务名
     */
    public static String getBusinessName(String tableName) {
        int firstIndex = tableName.indexOf("_");
        int nameLength = tableName.length();
        String businessName = StringUtils.substring(tableName, firstIndex + 1, nameLength);
        businessName = StringUtils.toCamelCase(businessName);
        return businessName;
    }

    /**
     * 表名转换成 Java 类名
     *
     * @param tableName 表名称
     * @return 类名
     */
    public static String convertClassName(String tableName) {
        boolean autoRemovePre = GeneratorConfig.getAutoRemovePre();
        String tablePrefix = GeneratorConfig.getTablePrefix();
        if (autoRemovePre && StringUtils.isNotEmpty(tablePrefix)) {
            String[] searchList = StringUtils.split(tablePrefix, StringUtils.SEPARATOR);
            tableName = replaceFirst(tableName, searchList);
        }
        return StringUtils.convertToCamelCase(tableName);
    }

    /**
     * 批量替换前缀
     *
     * @param replacementm 替换值
     * @param searchList   替换列表
     */
    public static String replaceFirst(String replacementm, String[] searchList) {
        String text = replacementm;
        for (String searchString : searchList) {
            if (replacementm.startsWith(searchString)) {
                text = replacementm.replaceFirst(searchString, StringUtils.EMPTY);
                break;
            }
        }
        return text;
    }

    /**
     * 关键字替换
     *
     * @param text 需要被替换的名字
     * @return 替换后的名字
     */
    public static String replaceText(String text) {
        return RegExUtils.replaceAll(text, "(?:表 | Dock)", "");
    }

    /**
     * 获取数据库类型字段
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    public static String getDbType(String columnType) {
        if (StringUtils.indexOf(columnType, "(") > 0) {
            return StringUtils.substringBefore(columnType, "(");
        } else {
            return columnType;
        }
    }

    /**
     * 获取字段长度
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    public static Integer getColumnLength(String columnType) {
        if (StringUtils.indexOf(columnType, "(") > 0) {
            String length = StringUtils.substringBetween(columnType, "(", ")");
            return Integer.valueOf(length);
        } else {
            return 0;
        }
    }
}
