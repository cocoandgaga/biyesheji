package com.zxj.finalexam.dto;


import lombok.Data;

import java.util.Date;
@Data
public class StuUserDTO {
    private Long userId;
    /**
     * 专业ID
     */
    private Long deptId;
    private String deptName;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐
     */
    private String salt;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;
    /**
     * 创建者ID
     */
    private Long createUserId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 用户昵称
     * */
    private String nickName;
}
