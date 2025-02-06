package com.ncobase.workflow.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ncobase.workflow.domain.bo.FlowInstanceBo;
import com.ncobase.workflow.domain.vo.FlowInstanceVo;
import org.apache.ibatis.annotations.Param;

/**
 * 实例信息 Mapper 接口
 *
 * @author may
 * @date 2024-03-02
 */
public interface FlwInstanceMapper {

    /**
     * 流程实例信息
     *
     * @param page         分页
     * @param queryWrapper 条件
     * @return 结果
     */
    Page<FlowInstanceVo> selectInstanceList(@Param("page") Page<FlowInstanceVo> page, @Param(Constants.WRAPPER) Wrapper<FlowInstanceBo> queryWrapper);

}
