package com.zxj.finalexam.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("NOTIFICATION")
public class Notification {
    private Long id;
    private Long notifier;
    private Long receiver;
    private Integer type;
    private Date gmtCreate;
    private Integer status;
    private String notifierName;
    private Long questionId ;
    private String content;
    //新家
    private Long commentPid;

}
