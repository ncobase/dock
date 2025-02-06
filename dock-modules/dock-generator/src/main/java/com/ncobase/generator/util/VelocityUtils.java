package com.ncobase.generator.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Dict;
import com.ncobase.framework.core.utils.DateUtils;
import com.ncobase.framework.core.utils.StringUtils;
import com.ncobase.generator.constant.GeneratorConstants;
import com.ncobase.generator.domain.GeneratorTable;
import com.ncobase.generator.domain.GeneratorTableColumn;
import com.ncobase.framework.json.utils.JsonUtils;
import com.ncobase.framework.mybatis.helper.DataBaseHelper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.velocity.VelocityContext;

import java.util.*;

/**
 * 模板处理工具类
 *
 * @author ruoyi
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VelocityUtils {

    /**
     * 项目空间路径
     */
    private static final String PROJECT_PATH = "main/java";

    /**
     * mybatis 空间路径
     */
    private static final String MYBATIS_PATH = "main/resources/mapper";

    /**
     * 默认上级菜单，系统工具
     */
    private static final String DEFAULT_PARENT_MENU_ID = "3";

    /**
     * 设置模板变量信息
     *
     * @return 模板列表
     */
    public static VelocityContext prepareContext(GeneratorTable generatorTable) {
        String moduleName = generatorTable.getModuleName();
        String businessName = generatorTable.getBusinessName();
        String packageName = generatorTable.getPackageName();
        String tplCategory = generatorTable.getTplCategory();
        String functionName = generatorTable.getFunctionName();

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tplCategory", generatorTable.getTplCategory());
        velocityContext.put("tableName", generatorTable.getTableName());
        velocityContext.put("functionName", StringUtils.isNotEmpty(functionName) ? functionName : "【请填写功能名称】");
        velocityContext.put("ClassName", generatorTable.getClassName());
        velocityContext.put("className", StringUtils.uncapitalize(generatorTable.getClassName()));
        velocityContext.put("moduleName", generatorTable.getModuleName());
        velocityContext.put("BusinessName", StringUtils.capitalize(generatorTable.getBusinessName()));
        velocityContext.put("businessName", generatorTable.getBusinessName());
        velocityContext.put("basePackage", getPackagePrefix(packageName));
        velocityContext.put("packageName", packageName);
        velocityContext.put("author", generatorTable.getFunctionAuthor());
        velocityContext.put("datetime", DateUtils.getDate());
        velocityContext.put("pkColumn", generatorTable.getPkColumn());
        velocityContext.put("importList", getImportList(generatorTable));
        velocityContext.put("permissionPrefix", getPermissionPrefix(moduleName, businessName));
        velocityContext.put("columns", generatorTable.getColumns());
        velocityContext.put("table", generatorTable);
        velocityContext.put("dicts", getDicts(generatorTable));
        setMenuVelocityContext(velocityContext, generatorTable);
        if (GeneratorConstants.TPL_TREE.equals(tplCategory)) {
            setTreeVelocityContext(velocityContext, generatorTable);
        }
        return velocityContext;
    }

    public static void setMenuVelocityContext(VelocityContext context, GeneratorTable generatorTable) {
        String options = generatorTable.getOptions();
        Dict paramsObj = JsonUtils.parseMap(options);
        String parentMenuId = getParentMenuId(paramsObj);
        context.put("parentMenuId", parentMenuId);
    }

    public static void setTreeVelocityContext(VelocityContext context, GeneratorTable generatorTable) {
        String options = generatorTable.getOptions();
        Dict paramsObj = JsonUtils.parseMap(options);
        String treeCode = getTreecode(paramsObj);
        String treeParentCode = getTreeParentCode(paramsObj);
        String treeName = getTreeName(paramsObj);

        context.put("treeCode", treeCode);
        context.put("treeParentCode", treeParentCode);
        context.put("treeName", treeName);
        context.put("expandColumn", getExpandColumn(generatorTable));
        if (paramsObj.containsKey(GeneratorConstants.TREE_PARENT_CODE)) {
            context.put("tree_parent_code", paramsObj.get(GeneratorConstants.TREE_PARENT_CODE));
        }
        if (paramsObj.containsKey(GeneratorConstants.TREE_NAME)) {
            context.put("tree_name", paramsObj.get(GeneratorConstants.TREE_NAME));
        }
    }

    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static List<String> getTemplateList(String tplCategory) {
        List<String> templates = new ArrayList<>();
        templates.add("vm/java/domain.java.vm");
        templates.add("vm/java/vo.java.vm");
        templates.add("vm/java/bo.java.vm");
        templates.add("vm/java/mapper.java.vm");
        templates.add("vm/java/service.java.vm");
        templates.add("vm/java/serviceImpl.java.vm");
        templates.add("vm/java/controller.java.vm");
        templates.add("vm/xml/mapper.xml.vm");
        if (DataBaseHelper.isOracle()) {
            templates.add("vm/sql/oracle/sql.vm");
        } else if (DataBaseHelper.isPostgerSql()) {
            templates.add("vm/sql/postgres/sql.vm");
        } else if (DataBaseHelper.isSqlServer()) {
            templates.add("vm/sql/sqlserver/sql.vm");
        } else {
            templates.add("vm/sql/sql.vm");
        }
        templates.add("vm/ts/api.ts.vm");
        templates.add("vm/ts/types.ts.vm");
        if (GeneratorConstants.TPL_CRUD.equals(tplCategory)) {
            templates.add("vm/vue/index.vue.vm");
        } else if (GeneratorConstants.TPL_TREE.equals(tplCategory)) {
            templates.add("vm/vue/index-tree.vue.vm");
        }
        return templates;
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, GeneratorTable generatorTable) {
        // 文件名称
        String fileName = "";
        // 包路径
        String packageName = generatorTable.getPackageName();
        // 模块名
        String moduleName = generatorTable.getModuleName();
        // 大写类名
        String className = generatorTable.getClassName();
        // 业务名称
        String businessName = generatorTable.getBusinessName();

        String javaPath = PROJECT_PATH + "/" + StringUtils.replace(packageName, ".", "/");
        String mybatisPath = MYBATIS_PATH + "/" + moduleName;
        String vuePath = "vue";

        if (template.contains("domain.java.vm")) {
            fileName = StringUtils.format("{}/domain/{}.java", javaPath, className);
        }
        if (template.contains("vo.java.vm")) {
            fileName = StringUtils.format("{}/domain/vo/{}Vo.java", javaPath, className);
        }
        if (template.contains("bo.java.vm")) {
            fileName = StringUtils.format("{}/domain/bo/{}Bo.java", javaPath, className);
        }
        if (template.contains("mapper.java.vm")) {
            fileName = StringUtils.format("{}/mapper/{}Mapper.java", javaPath, className);
        } else if (template.contains("service.java.vm")) {
            fileName = StringUtils.format("{}/service/I{}Service.java", javaPath, className);
        } else if (template.contains("serviceImpl.java.vm")) {
            fileName = StringUtils.format("{}/service/impl/{}ServiceImpl.java", javaPath, className);
        } else if (template.contains("controller.java.vm")) {
            fileName = StringUtils.format("{}/controller/{}Controller.java", javaPath, className);
        } else if (template.contains("mapper.xml.vm")) {
            fileName = StringUtils.format("{}/{}Mapper.xml", mybatisPath, className);
        } else if (template.contains("sql.vm")) {
            fileName = businessName + "Menu.sql";
        } else if (template.contains("api.ts.vm")) {
            fileName = StringUtils.format("{}/api/{}/{}/index.ts", vuePath, moduleName, businessName);
        } else if (template.contains("types.ts.vm")) {
            fileName = StringUtils.format("{}/api/{}/{}/types.ts", vuePath, moduleName, businessName);
        } else if (template.contains("index.vue.vm")) {
            fileName = StringUtils.format("{}/views/{}/{}/index.vue", vuePath, moduleName, businessName);
        } else if (template.contains("index-tree.vue.vm")) {
            fileName = StringUtils.format("{}/views/{}/{}/index.vue", vuePath, moduleName, businessName);
        }
        return fileName;
    }

    /**
     * 获取包前缀
     *
     * @param packageName 包名称
     * @return 包前缀名称
     */
    public static String getPackagePrefix(String packageName) {
        int lastIndex = packageName.lastIndexOf(".");
        return StringUtils.substring(packageName, 0, lastIndex);
    }

    /**
     * 根据列类型获取导入包
     *
     * @param generatorTable 业务表对象
     * @return 返回需要导入的包列表
     */
    public static HashSet<String> getImportList(GeneratorTable generatorTable) {
        List<GeneratorTableColumn> columns = generatorTable.getColumns();
        HashSet<String> importList = new HashSet<>();
        for (GeneratorTableColumn column : columns) {
            if (!column.isSuperColumn() && GeneratorConstants.TYPE_DATE.equals(column.getJavaType())) {
                importList.add("java.util.Date");
                importList.add("com.fasterxml.jackson.annotation.JsonFormat");
            } else if (!column.isSuperColumn() && GeneratorConstants.TYPE_BIGDECIMAL.equals(column.getJavaType())) {
                importList.add("java.math.BigDecimal");
            } else if (!column.isSuperColumn() && "imageUpload".equals(column.getHtmlType())) {
                importList.add("com.ncobase.framework.translation.annotation.Translation");
                importList.add("com.ncobase.framework.translation.constant.TransConstant");
            }
        }
        return importList;
    }

    /**
     * 根据列类型获取字典组
     *
     * @param generatorTable 业务表对象
     * @return 返回字典组
     */
    public static String getDicts(GeneratorTable generatorTable) {
        List<GeneratorTableColumn> columns = generatorTable.getColumns();
        Set<String> dicts = new HashSet<>();
        addDicts(dicts, columns);
        return StringUtils.join(dicts, ", ");
    }

    /**
     * 添加字典列表
     *
     * @param dicts   字典列表
     * @param columns 列集合
     */
    public static void addDicts(Set<String> dicts, List<GeneratorTableColumn> columns) {
        for (GeneratorTableColumn column : columns) {
            if (!column.isSuperColumn() && StringUtils.isNotEmpty(column.getDictType()) && StringUtils.equalsAny(
                column.getHtmlType(),
                new String[]{GeneratorConstants.HTML_SELECT, GeneratorConstants.HTML_RADIO, GeneratorConstants.HTML_CHECKBOX})) {
                dicts.add("'" + column.getDictType() + "'");
            }
        }
    }

    /**
     * 获取权限前缀
     *
     * @param moduleName   模块名称
     * @param businessName 业务名称
     * @return 返回权限前缀
     */
    public static String getPermissionPrefix(String moduleName, String businessName) {
        return StringUtils.format("{}:{}", moduleName, businessName);
    }

    /**
     * 获取上级菜单 ID 字段
     *
     * @param paramsObj 生成其他选项
     * @return 上级菜单 ID 字段
     */
    public static String getParentMenuId(Dict paramsObj) {
        if (CollUtil.isNotEmpty(paramsObj) && paramsObj.containsKey(GeneratorConstants.PARENT_MENU_ID)
            && StringUtils.isNotEmpty(paramsObj.getStr(GeneratorConstants.PARENT_MENU_ID))) {
            return paramsObj.getStr(GeneratorConstants.PARENT_MENU_ID);
        }
        return DEFAULT_PARENT_MENU_ID;
    }

    /**
     * 获取树编码
     *
     * @param paramsObj 生成其他选项
     * @return 树编码
     */
    public static String getTreecode(Map<String, Object> paramsObj) {
        if (CollUtil.isNotEmpty(paramsObj) && paramsObj.containsKey(GeneratorConstants.TREE_CODE)) {
            return StringUtils.toCamelCase(Convert.toStr(paramsObj.get(GeneratorConstants.TREE_CODE)));
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取树父编码
     *
     * @param paramsObj 生成其他选项
     * @return 树父编码
     */
    public static String getTreeParentCode(Dict paramsObj) {
        if (CollUtil.isNotEmpty(paramsObj) && paramsObj.containsKey(GeneratorConstants.TREE_PARENT_CODE)) {
            return StringUtils.toCamelCase(paramsObj.getStr(GeneratorConstants.TREE_PARENT_CODE));
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取树名称
     *
     * @param paramsObj 生成其他选项
     * @return 树名称
     */
    public static String getTreeName(Dict paramsObj) {
        if (CollUtil.isNotEmpty(paramsObj) && paramsObj.containsKey(GeneratorConstants.TREE_NAME)) {
            return StringUtils.toCamelCase(paramsObj.getStr(GeneratorConstants.TREE_NAME));
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取需要在哪一列上面显示展开按钮
     *
     * @param generatorTable 业务表对象
     * @return 展开按钮列序号
     */
    public static int getExpandColumn(GeneratorTable generatorTable) {
        String options = generatorTable.getOptions();
        Dict paramsObj = JsonUtils.parseMap(options);
        String treeName = paramsObj.getStr(GeneratorConstants.TREE_NAME);
        int num = 0;
        for (GeneratorTableColumn column : generatorTable.getColumns()) {
            if (column.isList()) {
                num++;
                String columnName = column.getColumnName();
                if (columnName.equals(treeName)) {
                    break;
                }
            }
        }
        return num;
    }
}
