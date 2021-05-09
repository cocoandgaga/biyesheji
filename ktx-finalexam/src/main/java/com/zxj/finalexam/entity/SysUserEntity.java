/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.zxj.finalexam.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户
 */
@Data
public class SysUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	

	private Long userId;
	private String username;
	private String password;
	private String salt;
	private String email;
	private String mobile;
	private Long deptId;
	/**
	 * 状态  0：禁用   1：正常
	 */
	private Integer status;
	private Long createUserId;
	private Date createTime;

}
