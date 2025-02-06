package com.ncobase.workflow.domain.vo;

import com.ncobase.framework.translation.annotation.Translation;
import com.ncobase.framework.translation.constant.TransConstant;
import com.ncobase.workflow.common.constant.FlowConstant;
import lombok.Data;

import java.util.Date;

/**
 * 流程实例视图
 *
 * @author may
 */
@Data
public class FlowInstanceVo {

    private Long id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 租户 ID
     */
    private String tenantId;

    /**
     * 删除标记
     */
    private String delFlag;

    /**
     * 对应 flow_definition 表的 id
     */
    private Long definitionId;

    /**
     * 流程定义名称
     */
    private String flowName;

    /**
     * 流程定义编码
     */
    private String flowCode;

    /**
     * 业务 id
     */
    private String businessId;

    /**
     * 节点类型（0 开始节点 1 中间节点 2 结束节点 3 互斥网关 4 并行网关）
     */
    private Integer nodeType;

    /**
     * 流程节点编码   每个流程的 nodeCode 是唯一的，即 definitionId+nodeCode 唯一，在数据库层面做了控制
     */
    private String nodeCode;

    /**
     * 流程节点名称
     */
    private String nodeName;

    /**
     * 流程变量
     */
    private String variable;

    /**
     * 流程状态（0 待提交 1 审批中 2 审批通过 3 自动通过 8 已完成 9 已退回 10 失效）
     */
    private String flowStatus;

    /**
     * 流程状态
     */
    private String flowStatusName;

    /**
     * 流程激活状态（0 挂起 1 激活）
     */
    private Integer activityStatus;

    /**
     * 审批表单是否自定义（Y 是 N 否）
     */
    private String formCustom;

    /**
     * 审批表单路径
     */
    private String formPath;

    /**
     * 扩展字段，预留给业务系统使用
     */
    private String ext;

    /**
     * 流程定义版本
     */
    private String version;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 申请人
     */
    @Translation(type = TransConstant.USER_ID_TO_NICKNAME, mapper = "createBy")
    private String createByName;

    /**
     * 流程分类 id
     */
    private String category;

    /**
     * 流程分类名称
     */
    @Translation(type = FlowConstant.CATEGORY_ID_TO_NAME, mapper = "category")
    private String categoryName;

}
