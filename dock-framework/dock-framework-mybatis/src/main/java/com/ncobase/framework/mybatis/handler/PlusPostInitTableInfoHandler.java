package com.ncobase.framework.mybatis.handler;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.handlers.PostInitTableInfoHandler;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.ncobase.framework.core.utils.SpringUtils;
import com.ncobase.framework.core.utils.reflect.ReflectUtils;
import org.apache.ibatis.session.Configuration;

/**
 * 修改表信息初始化方式
 * 目前用于全局修改是否使用逻辑删除
 *
 * @author Lion Li
 */
public class PlusPostInitTableInfoHandler implements PostInitTableInfoHandler {

    @Override
    public void postTableInfo(TableInfo tableInfo, Configuration configuration) {
        String flag = SpringUtils.getProperty("mybatis-plus.enableLogicDelete", "true");
        // 只有关闭时 统一设置 false 为 true 时 mp 自动判断不处理
        if (!Convert.toBool(flag)) {
            ReflectUtils.setFieldValue(tableInfo, "withLogicDelete", false);
        }
    }

}
