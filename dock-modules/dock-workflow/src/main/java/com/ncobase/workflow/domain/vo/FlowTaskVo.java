package com.ncobase.workflow.domain.vo;

import com.ncobase.framework.translation.annotation.Translation;
import com.ncobase.framework.translation.constant.TransConstant;
import com.ncobase.workflow.common.constant.FlowConstant;
import lombok.Data;
import org.dromara.warm.flow.core.entity.User;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 任务视图
 *
 * @author may
 */
@Data
public class FlowTaskVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
     * 流程实例表 id
     */
    private Long instanceId;

    /**
     * 流程定义名称
     */
    private String flowName;

    /**
     * 业务 id
     */
    private String businessId;

    /**
     * 节点编码
     */
    private String nodeCode;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 节点类型（0 开始节点 1 中间节点 2 结束节点 3 互斥网关 4 并行网关）
     */
    private Integer nodeType;

    /**
     * 权限标识 permissionFlag 的 list 形式
     */
    private List<String> permissionList;

    /**
     * 流程用户列表
     */
    private List<User> userList;

    /**
     * 审批表单是否自定义（Y 是 N 否）
     */
    private String formCustom;

    /**
     * 审批表单
     */
    private String formPath;

    /**
     * 流程定义编码
     */
    private String flowCode;

    /**
     * 流程版本号
     */
    private String version;

    /**
     * 流程状态
     */
    private String flowStatus;

    /**
     * 流程分类 id
     */
    private String category;

    /**
     * 流程分类名称
     */
    @Translation(type = FlowConstant.CATEGORY_ID_TO_NAME, mapper = "category")
    private String categoryName;

    /**
     * 流程状态
     */
    @Translation(type = TransConstant.DICT_TYPE_TO_LABEL, mapper = "flowStatus", other = "wf_business_status")
    private String flowStatusName;

    /**
     * 办理人类型
     */
    private String type;

    /**
     * 办理人 ids
     */
    private String assigneeIds;

    /**
     * 办理人名称
     */
    private String assigneeNames;

    /**
     * 抄送人 id
     */
    private String processedBy;

    /**
     * 抄送人名称
     */
    @Translation(type = TransConstant.USER_ID_TO_NICKNAME, mapper = "processedBy")
    private String processedByName;

    /**
     * 流程签署比例值 大于 0 为票签，会签
     */
    private BigDecimal nodeRatio;

    /**
     * 申请人 id
     */
    private String createBy;

    /**
     * 申请人名称
     */
    @Translation(type = TransConstant.USER_ID_TO_NICKNAME, mapper = "createBy")
    private String createByName;
}
