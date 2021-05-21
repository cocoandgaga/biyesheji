package com.zxj.finalexam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-04-12 19:44:13
 */
@Data
@TableName("user_task")
public class UserTaskEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId(value = "USER_TASK_ID", type = IdType.UUID)
	private String userTaskId;
	private Long userId;
	private String userName;
	private String userTaskName;
	private String userTaskDescription;
	private String subjectName;
	private Long deptId;
	private BigDecimal score;
	/**
	 * 1 发布中 2 已截止
	 */
	private Integer userTaskStatus;
	private Date startTime;
	private Date endTime;

	private Integer objStatus;

}
