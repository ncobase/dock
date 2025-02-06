package com.ncobase.workflow.controller;

import com.ncobase.framework.core.domain.R;
import com.ncobase.framework.idempotent.annotation.RepeatSubmit;
import com.ncobase.framework.logger.annotation.Log;
import com.ncobase.framework.logger.enums.BusinessType;
import com.ncobase.framework.mybatis.core.page.PageQuery;
import com.ncobase.framework.mybatis.core.page.TableDataInfo;
import com.ncobase.framework.web.web.core.BaseController;
import com.ncobase.workflow.common.ConditionalOnEnable;
import com.ncobase.workflow.domain.bo.FlowCancelBo;
import com.ncobase.workflow.domain.bo.FlowInstanceBo;
import com.ncobase.workflow.domain.bo.FlowInvalidBo;
import com.ncobase.workflow.domain.vo.FlowInstanceVo;
import com.ncobase.workflow.service.IFlwInstanceService;
import lombok.RequiredArgsConstructor;
import org.dromara.warm.flow.core.service.InsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 流程实例管理 控制层
 *
 * @author may
 */
@ConditionalOnEnable
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/instance")
public class FlwInstanceController extends BaseController {

    private final InsService insService;
    private final IFlwInstanceService flwInstanceService;

    /**
     * 查询正在运行的流程实例列表
     *
     * @param flowInstanceBo 流程实例
     * @param pageQuery      分页
     */
    @GetMapping("/pageByRunning")
    public TableDataInfo<FlowInstanceVo> selectRunningInstanceList(FlowInstanceBo flowInstanceBo, PageQuery pageQuery) {
        return flwInstanceService.selectRunningInstanceList(flowInstanceBo, pageQuery);
    }

    /**
     * 查询已结束的流程实例列表
     *
     * @param flowInstanceBo 流程实例
     * @param pageQuery      分页
     */
    @GetMapping("/pageByFinish")
    public TableDataInfo<FlowInstanceVo> selectFinishInstanceList(FlowInstanceBo flowInstanceBo, PageQuery pageQuery) {
        return flwInstanceService.selectFinishInstanceList(flowInstanceBo, pageQuery);
    }

    /**
     * 根据业务 id 查询流程实例详细信息
     *
     * @param businessId 业务 id
     */
    @GetMapping("/getInfo/{businessId}")
    public R<FlowInstanceVo> getInfo(@PathVariable Long businessId) {
        return R.ok(flwInstanceService.queryByBusinessId(businessId));
    }

    /**
     * 按照业务 id 删除流程实例
     *
     * @param businessIds 业务 id
     */
    @DeleteMapping("/deleteByBusinessIds/{businessIds}")
    public R<Void> deleteByBusinessIds(@PathVariable List<Long> businessIds) {
        return toAjax(flwInstanceService.deleteByBusinessIds(businessIds));
    }

    /**
     * 按照实例 id 删除流程实例
     *
     * @param instanceIds 实例 id
     */
    @DeleteMapping("/deleteByInstanceIds/{instanceIds}")
    public R<Void> deleteByInstanceIds(@PathVariable List<Long> instanceIds) {
        return toAjax(flwInstanceService.deleteByInstanceIds(instanceIds));
    }

    /**
     * 撤销流程
     *
     * @param bo 参数
     */
    @RepeatSubmit()
    @PutMapping("/cancelProcessApply")
    public R<Void> cancelProcessApply(@RequestBody FlowCancelBo bo) {
        return toAjax(flwInstanceService.cancelProcessApply(bo));
    }

    /**
     * 激活/挂起流程实例
     *
     * @param id     流程实例 id
     * @param active 激活/挂起
     */
    @RepeatSubmit()
    @PutMapping("/active/{id}")
    public R<Boolean> active(@PathVariable Long id, @RequestParam boolean active) {
        return R.ok(active ? insService.active(id) : insService.unActive(id));
    }

    /**
     * 获取当前登陆人发起的流程实例
     *
     * @param flowInstanceBo 参数
     * @param pageQuery      分页
     */
    @GetMapping("/pageByCurrent")
    public TableDataInfo<FlowInstanceVo> selectCurrentInstanceList(FlowInstanceBo flowInstanceBo, PageQuery pageQuery) {
        return flwInstanceService.selectCurrentInstanceList(flowInstanceBo, pageQuery);
    }

    /**
     * 获取流程图，流程记录
     *
     * @param businessId 业务 id
     */
    @GetMapping("/flowImage/{businessId}")
    public R<Map<String, Object>> flowImage(@PathVariable String businessId) {
        return R.ok(flwInstanceService.flowImage(businessId));
    }

    /**
     * 获取流程变量
     *
     * @param instanceId 流程实例 id
     */
    @GetMapping("/instanceVariable/{instanceId}")
    public R<Map<String, Object>> instanceVariable(@PathVariable Long instanceId) {
        return R.ok(flwInstanceService.instanceVariable(instanceId));
    }

    /**
     * 作废流程
     *
     * @param bo 参数
     */
    @Log(title = "流程实例管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/invalid")
    public R<Boolean> invalid(@Validated @RequestBody FlowInvalidBo bo) {
        return R.ok(flwInstanceService.processInvalid(bo));
    }

}
