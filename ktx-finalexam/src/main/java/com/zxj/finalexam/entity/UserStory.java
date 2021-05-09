package com.zxj.finalexam.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2019-04-19
 */
@Slf4j
@Data
@Accessors(chain = true)
@TableName("t_user_story")
public class UserStory implements Serializable ,Cloneable{

    private static final long serialVersionUID = 1L;

    /**
     * 老师创建的任务ID
     */
    @TableId(value = "USER_TASK_ID", type = IdType.UUID)
    private String userTaskId;
    /**
     * 关联的系Id
     */
    @TableField("DEPT_ID")
    private Long deptId;

    @TableField("SUBJECT_NAME")
    private Long subjectName;
    /**
     * 故事名称
     */
    @TableField("Task_Name")
    private String TaskName;
    /**
     * 描述
     */
    @TableField("DESCRIPTION")
    private String description;
    /**
     * 用户故事状态来源字典 0-待确认 1-以澄清 2-进行中 3-待验收 4-已验收 5-已上线
     */
    @TableField("STATUS")
    private String status;
    /**
     * 创建人
     */
    @TableField("USER_ID")
    private String userId;
    @TableField(exist = false)
    private String createUserName;
    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;
    /**
     * 故事集ID
     */
    @TableField("GROUP_ID")
    private String groupId;
    @TableField(exist = false)
    private String groupName;

    @TableField("UPDATE_USER")
    private String updateUser;
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /**
     * 实际开始时间
     */
    @TableField("ACTUAL_DATE_START")
    private Date actualDateStart;
    @TableField("ACTUAL_DATE_END")
    private Date actualDateEnd;

    /**
     * 实际开始时间
     */
    @TableField(exist = false)
    private Date actualStartTime;
    /**
     * 实际结束时间
     */
    @TableField(exist = false)
    private Date actualEndTime;



    @TableField(exist = false)
    private String statusName;

    @TableField(exist = false)
    private String priorityName;

    /**
     * 用户故事拆分的卡片数量
     */
    @TableField(exist = false)
    private Integer taskCardCount;
    /**
     * 用户故事拆分卡片任务参与人 “,”隔开
     */
    @TableField(exist = false)
    private String taskUserIds;
    /**
     * 用户故事拆分卡片任务参与人 “,”隔开
     */
    @TableField(exist = false)
    private String taskUserNames;

    /**
     * 任务名称
     */
    @TableField(exist = false)
    private String taskName;

    /**
     * 任务处理人
     */
    @TableField(exist = false)
    private String taskUserId;
    @TableField(exist = false)
    private String taskUserName;
    @TableField(exist = false)
    private String taskDescription;
}
