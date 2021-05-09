package com.zxj.finalexam.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * 
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-02-18 19:52:00
 */
@Data
@TableName("QUESTION")
public class QuestionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Long id; 
	private String title; 
	private String description;
	private  Date gmtCreate;
	private Date gmtModified;
	private Long creator;
	private Long viewCnt;
	private Long likeCnt;
	private Long commentCnt;
	private String tags;
	private String content;
	private Integer type;
	@TableField(exist = false)
	private String creatorName;
	@TableField(exist = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date beginTime;
	@TableField(exist = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endTime;

}
