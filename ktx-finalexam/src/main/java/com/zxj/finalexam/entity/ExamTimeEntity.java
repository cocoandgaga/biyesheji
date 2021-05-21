package com.zxj.finalexam.entity;

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
 * @date 2021-05-15 10:02:03
 */
@Data
@TableName("EXAM_TIME")
public class ExamTimeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long deptId;
	/**
	 * 课程名
	 */
	private String courseName;
	/**
	 * 考试时间
	 */
	private Date startTime;

	private Date endTime;
	/**
	 * 考试地点
	 */
	private String place;
	/**
	 * 座位号
	 */
	private Integer seatNum;

}
