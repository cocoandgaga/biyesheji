package com.zxj.finalexam.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zxj.finalexam.entity.FreeColumnItem;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * 用户故事卡片实体类
 * @author TianLong
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Accessors(chain = true)
public class UserStoryCard {

    private String userTaskId;
    private String userId;
    private String userName;
    private String userTaskName;
    private String userTaskDescription;
    private String subjectName;
    private String deptId;
    private String userTaskStatus;
    private Date startTime;
    private Date endTime;
    private FreeColumnItem position;
    private List<TaskCard> taskCardList;
    private Integer sort;
    private Integer messageCount;
}
