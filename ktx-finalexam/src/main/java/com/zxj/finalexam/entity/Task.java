package com.zxj.finalexam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 任务
 * </p>
 *
 * @author ${author}
 * @since 2019-04-19
 */
@Data
@Accessors(chain = true)
@TableName("t_task")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务标识
     */
    @TableId(value = "TASK_ID", type = IdType.UUID)
    private String taskId;
    /**
     * 所属老师发布任务的id
     */
    @TableField("USER_TASK_ID")
    private String userTaskId;

    @TableField("DEPT_ID")
    private Long DEPT_ID;
    /**
     * 计划项标识
     */
    @TableField("ITEM_ID")
    private String itemId;
    /**
     * 任务处理人标识
     */
    @TableField("STU_ID")
    private Long stuId;
    /**
     * 任务处理人
     */
    @TableField("STU_NAME")
    private String userName;
    /**
     * 描述
     */
    @TableField("DESCRIPTION")
    private String description;
    /**
     * 任务名称
     */
    @TableField("TASK_NAME")
    private String taskName;
    /**
     * 实际开始时间
     */
    @TableField("ACTUAL_DATE_START")
    private Date actualDateStart;
    @TableField("ACTUAL_DATE_END")
    private Date actualDateEnd;
    /**
     * 任务状态：1待确认、2进行中、3完成
     */
    @TableField("STATUS")
    private Integer status;


    @TableField(exist = false)
    private String storyName;

    @TableField(exist = false)
    private String statusName;

    @TableField(exist = false)
    private List<CardMessageEntity> cardMessageEntityList;

    @TableField(exist = false)
    private Integer statusColumnId;

}
