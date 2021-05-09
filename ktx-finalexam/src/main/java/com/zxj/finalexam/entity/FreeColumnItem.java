package com.zxj.finalexam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2019-04-19
 */
@Data
@Accessors(chain = true)
@TableName("free_column_item")
public class FreeColumnItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 卡片计划项对应自定义列及列状态，即看板位置
     */
    @TableId(value = "SEQ_ID", type = IdType.UUID)
    private String seqId;

    /**
     * 卡片所属的用户故事Id
     */
    @TableField("USER_TASK_ID")
    private String userTaskId;
    /**
     * 敏捷看板所在类型列
     */
    @TableField("COLUMN_ID")
    private String columnId;
    /**
     * 敏捷看板所在状态列
     */
    @TableField("STATUS_COLUMN_ID")
    private Integer statusColumnId;

    /**
     * 关联的任务ID，故事卡片无此ID
     */
    @TableField("STU_TASK_ID")
    private String stuTaskId;

    @TableField("SORT")
    private Integer sort;

    @TableField("TYPE")
    private Integer type;

    @TableField(exist = false)
    private Integer columnTypeSort;

    @TableField(exist = false)
    private Boolean clearTask;

}
