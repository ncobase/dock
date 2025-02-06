package com.ncobase.workflow.service;

import com.ncobase.framework.mybatis.core.page.PageQuery;
import com.ncobase.framework.mybatis.core.page.TableDataInfo;
import com.ncobase.workflow.domain.bo.FlowCancelBo;
import com.ncobase.workflow.domain.bo.FlowInstanceBo;
import com.ncobase.workflow.domain.bo.FlowInvalidBo;
import com.ncobase.workflow.domain.vo.FlowInstanceVo;
import org.dromara.warm.flow.orm.entity.FlowInstance;

import java.util.List;
import java.util.Map;

/**
 * 流程实例 服务层
 *
 * @author may
 */
public interface IFlwInstanceService {

    /**
     * 分页查询正在运行的流程实例
     *
     * @param flowInstanceBo 流程实例
     * @param pageQuery      分页
     * @return 结果
     */
    TableDataInfo<FlowInstanceVo> selectRunningInstanceList(FlowInstanceBo flowInstanceBo, PageQuery pageQuery);

    /**
     * 分页查询已结束的流程实例
     *
     * @param flowInstanceBo 流程实例
     * @param pageQuery      分页
     * @return 结果
     */
    TableDataInfo<FlowInstanceVo> selectFinishInstanceList(FlowInstanceBo flowInstanceBo, PageQuery pageQuery);

    /**
     * 根据业务 id 查询流程实例详细信息
     *
     * @param businessId 业务 id
     * @return 结果
     */
    FlowInstanceVo queryByBusinessId(Long businessId);

    /**
     * 按照业务 id 查询流程实例
     *
     * @param businessId 业务 id
     * @return 结果
     */
    FlowInstance selectInstByBusinessId(String businessId);

    /**
     * 按照实例 id 查询流程实例
     *
     * @param instanceId 实例 id
     * @return 结果
     */
    FlowInstance selectInstById(Long instanceId);

    /**
     * 按照实例 id 查询流程实例
     *
     * @param instanceIds 实例 id
     * @return 结果
     */
    List<FlowInstance> selectInstListByIdList(List<Long> instanceIds);

    /**
     * 按照业务 id 删除流程实例
     *
     * @param businessIds 业务 id
     * @return 结果
     */
    boolean deleteByBusinessIds(List<Long> businessIds);

    /**
     * 按照实例 id 删除流程实例
     *
     * @param instanceIds 实例 id
     * @return 结果
     */
    boolean deleteByInstanceIds(List<Long> instanceIds);

    /**
     * 撤销流程
     *
     * @param bo 参数
     * @return 结果
     */
    boolean cancelProcessApply(FlowCancelBo bo);

    /**
     * 获取当前登陆人发起的流程实例
     *
     * @param instanceBo 流程实例
     * @param pageQuery  分页
     * @return 结果
     */
    TableDataInfo<FlowInstanceVo> selectCurrentInstanceList(FlowInstanceBo instanceBo, PageQuery pageQuery);

    /**
     * 获取流程图，流程记录
     *
     * @param businessId 业务 id
     * @return 结果
     */
    Map<String, Object> flowImage(String businessId);

    /**
     * 按照实例 id 更新状态
     *
     * @param instanceId 实例 id
     * @param status     状态
     */
    void updateStatus(Long instanceId, String status);

    /**
     * 获取流程变量
     *
     * @param instanceId 实例 id
     * @return 结果
     */
    Map<String, Object> instanceVariable(Long instanceId);

    /**
     * 设置流程变量
     *
     * @param instanceId 实例 id
     * @param variable   流程变量
     */
    void setVariable(Long instanceId, Map<String, Object> variable);

    /**
     * 按任务 id 查询实例
     *
     * @param taskId 任务 id
     * @return 结果
     */
    FlowInstance selectByTaskId(Long taskId);

    /**
     * 按任务 id 查询实例
     *
     * @param taskIdList 任务 id
     * @return 结果
     */
    List<FlowInstance> selectByTaskIdList(List<Long> taskIdList);

    /**
     * 作废流程
     *
     * @param bo 流程实例
     * @return 结果
     */
    boolean processInvalid(FlowInvalidBo bo);
}
