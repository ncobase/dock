package com.ncobase.workflow.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ncobase.framework.core.domain.event.ProcessDeleteEvent;
import com.ncobase.framework.core.domain.event.ProcessEvent;
import com.ncobase.framework.core.domain.event.ProcessTaskEvent;
import com.ncobase.framework.core.enums.BusinessStatusEnum;
import com.ncobase.framework.core.service.WorkflowService;
import com.ncobase.framework.core.utils.MapstructUtils;
import com.ncobase.framework.core.utils.StringUtils;
import com.ncobase.framework.mybatis.core.domain.BaseEntity;
import com.ncobase.framework.mybatis.core.page.PageQuery;
import com.ncobase.framework.mybatis.core.page.TableDataInfo;
import com.ncobase.workflow.common.ConditionalOnEnable;
import com.ncobase.workflow.domain.TestLeave;
import com.ncobase.workflow.domain.bo.TestLeaveBo;
import com.ncobase.workflow.domain.vo.TestLeaveVo;
import com.ncobase.workflow.mapper.TestLeaveMapper;
import com.ncobase.workflow.service.ITestLeaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 请假 Service 业务层处理
 *
 * @author may
 * @date 2023-07-21
 */
@ConditionalOnEnable
@RequiredArgsConstructor
@Service
@Slf4j
public class TestLeaveServiceImpl implements ITestLeaveService {

    private final TestLeaveMapper baseMapper;
    private final WorkflowService workflowService;

    /**
     * 查询请假
     */
    @Override
    public TestLeaveVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询请假列表
     */
    @Override
    public TableDataInfo<TestLeaveVo> queryPageList(TestLeaveBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TestLeave> lqw = buildQueryWrapper(bo);
        Page<TestLeaveVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询请假列表
     */
    @Override
    public List<TestLeaveVo> queryList(TestLeaveBo bo) {
        LambdaQueryWrapper<TestLeave> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TestLeave> buildQueryWrapper(TestLeaveBo bo) {
        LambdaQueryWrapper<TestLeave> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getLeaveType()), TestLeave::getLeaveType, bo.getLeaveType());
        lqw.ge(bo.getStartLeaveDays() != null, TestLeave::getLeaveDays, bo.getStartLeaveDays());
        lqw.le(bo.getEndLeaveDays() != null, TestLeave::getLeaveDays, bo.getEndLeaveDays());
        lqw.orderByDesc(BaseEntity::getCreateTime);
        return lqw;
    }

    /**
     * 新增请假
     */
    @Override
    public TestLeaveVo insertByBo(TestLeaveBo bo) {
        long day = DateUtil.betweenDay(bo.getStartDate(), bo.getEndDate(), true);
        // 截止日期也算一天
        bo.setLeaveDays((int) day + 1);
        TestLeave add = MapstructUtils.convert(bo, TestLeave.class);
        if (StringUtils.isBlank(add.getStatus())) {
            add.setStatus(BusinessStatusEnum.DRAFT.getStatus());
        }
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return MapstructUtils.convert(add, TestLeaveVo.class);
    }

    /**
     * 修改请假
     */
    @Override
    public TestLeaveVo updateByBo(TestLeaveBo bo) {
        TestLeave update = MapstructUtils.convert(bo, TestLeave.class);
        baseMapper.updateById(update);
        return MapstructUtils.convert(update, TestLeaveVo.class);
    }

    /**
     * 批量删除请假
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteWithValidByIds(List<Long> ids) {
        workflowService.deleteInstance(ids);
        return baseMapper.deleteByIds(ids) > 0;
    }

    /**
     * 总体流程监听 (例如：草稿，撤销，退回，作废，终止，已完成等)
     * 正常使用只需#processEvent.flowCode=='leave1'
     * 示例为了方便则使用 startsWith 匹配了全部示例 key
     *
     * @param processEvent 参数
     */
    @EventListener(condition = "#processEvent.flowCode.startsWith('leave')")
    public void processHandler(ProcessEvent processEvent) {
        log.info("当前任务执行了{}", processEvent.toString());
        TestLeave testLeave = baseMapper.selectById(Long.valueOf(processEvent.getBusinessId()));
        testLeave.setStatus(processEvent.getStatus());
        // 用于例如审批附件 审批意见等 存储到业务表内 自行根据业务实现存储流程
        Map<String, Object> params = processEvent.getParams();
        if (MapUtil.isNotEmpty(params)) {
            // 历史任务扩展 (通常为附件)
            String hisTaskExt = Convert.toStr(params.get("hisTaskExt"));
            // 办理人
            String handler = Convert.toStr(params.get("handler"));
            // 办理意见
            String message = Convert.toStr(params.get("message"));
        }
        if (processEvent.isSubmit()) {
            testLeave.setStatus(BusinessStatusEnum.WAITING.getStatus());
        }
        baseMapper.updateById(testLeave);
    }

    /**
     * 执行办理任务监听
     * 示例：也可通过  @EventListener(condition = "#processTaskEvent.flowCode=='leave1'") 进行判断
     * 在方法中判断流程节点 key
     * if ("xxx".equals(processTaskEvent.getNodeCode())) {
     * //执行业务逻辑
     * }
     *
     * @param processTaskEvent 参数
     */
    @EventListener(condition = "#processTaskEvent.flowCode.startsWith('leave')")
    public void processTaskHandler(ProcessTaskEvent processTaskEvent) {
        log.info("当前任务执行了{}", processTaskEvent.toString());
        TestLeave testLeave = baseMapper.selectById(Long.valueOf(processTaskEvent.getBusinessId()));
        testLeave.setStatus(BusinessStatusEnum.WAITING.getStatus());
        baseMapper.updateById(testLeave);
    }

    /**
     * 监听删除流程事件
     * 正常使用只需#processDeleteEvent.flowCode=='leave1'
     * 示例为了方便则使用 startsWith 匹配了全部示例 key
     *
     * @param processDeleteEvent 参数
     */
    @EventListener(condition = "#processDeleteEvent.flowCode.startsWith('leave')")
    public void processDeleteHandler(ProcessDeleteEvent processDeleteEvent) {
        log.info("监听删除流程事件，当前任务执行了{}", processDeleteEvent.toString());
        TestLeave testLeave = baseMapper.selectById(Long.valueOf(processDeleteEvent.getBusinessId()));
        if (ObjectUtil.isNull(testLeave)) {
            return;
        }
        baseMapper.deleteById(testLeave.getId());
    }

}
