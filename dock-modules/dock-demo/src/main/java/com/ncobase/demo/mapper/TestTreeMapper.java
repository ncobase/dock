package com.ncobase.demo.mapper;

import com.ncobase.demo.domain.TestTree;
import com.ncobase.demo.domain.vo.TestTreeVo;
import com.ncobase.framework.mybatis.annotation.DataColumn;
import com.ncobase.framework.mybatis.annotation.DataPermission;
import com.ncobase.framework.mybatis.core.mapper.BaseMapperPlus;

/**
 * 测试树表 Mapper 接口
 *
 * @author Lion Li
 * @date 2021-07-26
 */
@DataPermission({
    @DataColumn(key = "deptName", value = "dept_id"),
    @DataColumn(key = "userName", value = "user_id")
})
public interface TestTreeMapper extends BaseMapperPlus<TestTree, TestTreeVo> {

}
