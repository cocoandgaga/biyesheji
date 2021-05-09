package com.zxj.finalexam.vo;

import com.zxj.finalexam.entity.FreeColumnItem;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * @author TianLong
 */
@Data
@Accessors(chain = true)
public class TaskCard{

    private String stuTaskId;
    private String userTaskId;
    private String stuTaskName;
    private String stuTaskDescription;
    private Integer stuTaskStatus;//1待确认 2进行中 3完成
    private Date startTime;
    private Date endTime;
    private String stuId;
    private String stuName;
    private Long deptId;
    private String subjectName;
    private Integer messageCount;
    private FreeColumnItem position;
}
