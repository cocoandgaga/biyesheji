package com.zxj.finalexam.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * 
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-04-03 19:16:26
 */
@Data
@TableName("DEPT_USER")
public class DeptUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Long id;
	@TableField("dept_id")
	private Long deptId;
	private Long userId;
	@TableField(exist = false)
	private String name;
	@TableField(exist = false)
	private String username;

}
