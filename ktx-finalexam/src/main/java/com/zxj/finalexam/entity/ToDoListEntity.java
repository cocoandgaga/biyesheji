package com.zxj.finalexam.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 复习清单
 * 
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-03-10 00:50:13
 */
@Data
@TableName("TO_DO_LIST")
public class ToDoListEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String descp;
	/**
	 * 
	 */
	private Date gmtCreate;
	/**
	 * 
	 */
	private Date gmtModified;
	/**
	 * 
	 */
	private Date startTime;
	/**
	 * 
	 */
	private Date endTime;
	/**
	 * 0未完成 1待办 2正在完成 3已完成
	 */
	private Integer isCompleted;
	/**
	 * 
	 */
	private String subjectName;
	/**
	 * 
	 */
	private String teacherName;
	/**
	 * 0老师计划 1学生计划
	 */
	private Integer type;
	/**
	 * 完成数目
	 */
	private Integer completeNum;

}
