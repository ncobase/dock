package com.ncobase.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ncobase.demo.domain.TestDemo;
import com.ncobase.demo.domain.vo.TestDemoVo;
import com.ncobase.framework.mybatis.annotation.DataColumn;
import com.ncobase.framework.mybatis.annotation.DataPermission;
import com.ncobase.framework.mybatis.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 测试单表 Mapper 接口
 *
 * @author Lion Li
 * @date 2021-07-26
 */
public interface TestDemoMapper extends BaseMapperPlus<TestDemo, TestDemoVo> {

    @DataPermission({
        @DataColumn(key = "deptName", value = "dept_id"),
        @DataColumn(key = "userName", value = "user_id")
    })
    Page<TestDemoVo> customPageList(@Param("page") Page<TestDemo> page, @Param("ew") Wrapper<TestDemo> wrapper);

    @Override
    @DataPermission({
        @DataColumn(key = "deptName", value = "dept_id"),
        @DataColumn(key = "userName", value = "user_id")
    })
    default <P extends IPage<TestDemoVo>> P selectVoPage(IPage<TestDemo> page, Wrapper<TestDemo> wrapper) {
        return selectVoPage(page, wrapper, this.currentVoClass());
    }

    @Override
    @DataPermission({
        @DataColumn(key = "deptName", value = "dept_id"),
        @DataColumn(key = "userName", value = "user_id")
    })
    default List<TestDemoVo> selectVoList(Wrapper<TestDemo> wrapper) {
        return selectVoList(wrapper, this.currentVoClass());
    }

    @Override
    @DataPermission(value = {
        @DataColumn(key = "deptName", value = "dept_id"),
        @DataColumn(key = "userName", value = "user_id")
    }, joinStr = "AND")
    List<TestDemo> selectByIds(@Param(Constants.COLL) Collection<? extends Serializable> idList);

    @Override
    @DataPermission({
        @DataColumn(key = "deptName", value = "dept_id"),
        @DataColumn(key = "userName", value = "user_id")
    })
    int updateById(@Param(Constants.ENTITY) TestDemo entity);

}
