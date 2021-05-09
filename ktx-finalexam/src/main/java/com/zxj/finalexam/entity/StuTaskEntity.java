package com.zxj.finalexam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
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
@TableName("stu_task")
public class StuTaskEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(value = "STU_TASK_ID", type = IdType.UUID)
	private String stuTaskId;
	/**
	 * 所属老师发布的任务
	 */
	private String userTaskId;
	/**
	 * 
	 */
	private Long stuId;
	/**
	 * 
	 */
	private String stuName;
	/**
	 * 
	 */
	private String stuTaskName;
	/**
	 * 
	 */
	private String stuTaskDescription;
	/**
	 * 
	 */
	private Long deptId;
	/**
	 * 
	 */
	private Date startTime;
	/**
	 * 
	 */
	private Date endTime;
	/**
	 * 1待确认 2进行中 3完成
	 */
	private Integer stuTaskStatus;
	private String subjectName;

}
