package com.zxj.finalexam.vo;

import lombok.Data;

/**
 * 看板用户故事模型
 */
@Data
public class UserStoryBoard {
    private String userStoryId;
    private String userStoryCode;
    private Integer userStorySeq;
    private String description;
    private Integer objStatus;
    private String agilePlanId;
    private String teamId;
}
