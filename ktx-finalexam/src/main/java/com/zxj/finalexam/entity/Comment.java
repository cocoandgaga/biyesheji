package com.zxj.finalexam.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("COMMENT")
public class Comment {

    @TableId
    private Long id;
    private Long parentId;
    private Integer type;
    private String content;
    private Long commentator;
    private Date gmtCreate;
    private Long likeCnt;
    private Long commentCnt;
    //被回复者姓名
    private String replierName;
    @TableField(exist = false)
    private boolean show=false;
    @TableField(exist = false)
    private Long commentPid;

    @TableField(exist = false)
    private String creatorName;
    @TableField(exist = false)
    private Long totalCnt;
    @TableField(exist = false)
    private Long notifier;
    @TableField(exist = false)
    private Long receiver;
    @TableField(exist = false)
    private Long questionId;
}
