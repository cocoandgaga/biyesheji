package com.zxj.finalexam.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 前台用户
 * 
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-03-25 00:27:07
 */
@Data
@TableName("STU_USER")
public class StuUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Long userId;
	/**
	 * 专业ID
	 */
	private Long deptId;
	private Integer grade;

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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	/**
	 * 修改时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime;
	/**
	 * 是否删除0否1删除
	 */
	private Integer objStatus;
    /**
	 * 用户昵称
	 * */
	private String nickName;

	@TableField(exist = false)
	private String deptName;

}
