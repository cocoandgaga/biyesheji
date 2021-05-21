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
 * @date 2021-05-16 23:04:59
 */
@Data
@TableName("course_score")
public class CourseScoreEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId(value = "ID", type = IdType.UUID)
	private String id;
	private Long stuId;
	private Long deptId;
	private BigDecimal score;
	private String subjectName;
	private String userTaskId;
	private String taskName;

}
