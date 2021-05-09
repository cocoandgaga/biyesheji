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
 * @date 2021-02-18 19:52:00
 */
@Data
@TableName("STUDY_MATERIALS")
public class StudyMaterialsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private String description;
	/**
	 * 
	 */
	private Long cmtCreate;
	/**
	 * 
	 */
	private Long gmtModified;
	/**
	 * 
	 */
	private Long creator;
	/**
	 * 
	 */
	private Integer viewCnt;
	/**
	 * 
	 */
	private Integer likeCnt;
	/**
	 * 
	 */
	private Integer commentCnt;
	/**
	 * 
	 */
	private String tag;
	/**
	 * 
	 */
	private String imageUrl;
	/**
	 * 
	 */
	private String videoUrl;

}
