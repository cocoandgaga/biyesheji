package com.zxj.finalexam.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2019-07-24
 */
@Data
@Accessors(chain = true)
@TableName("card_message")
public class CardMessageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;
    /**
     * 所属卡片
     */
    @TableField("STU_TASK_ID")
    private String StuTaskId;
    @TableField("USER_TASK_ID")
    private String userTaskId;
    /**
     * 用户
     */
    @TableField("USER_ID")
    private Long userId;

    @TableField(exist = false)
    private String userName;
    @TableField("TYPE")
    private Integer type;
    /**
     * 留言内容
     */
    @TableField("CONTENT")
    private String content;
    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField(exist = false)
    private BigDecimal score;
    @TableField(exist = false)
    private String subjectName;
    @TableField(exist = false)
    private String taskName;
}
