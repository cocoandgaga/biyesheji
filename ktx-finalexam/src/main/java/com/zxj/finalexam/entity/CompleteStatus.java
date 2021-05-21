package com.zxj.finalexam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("COMPLETE_STATUS")
public class CompleteStatus {
    @TableId(value = "ID", type = IdType.UUID)
    private String id;
    private String userTaskId;
    private Long stuId;
    private Date gmtCreate;
    private Integer status;
    private Integer score;
}
