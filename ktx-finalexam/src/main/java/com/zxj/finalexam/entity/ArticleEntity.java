package com.zxj.finalexam.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-02-18 19:51:59
 */
@Data
@TableName("ARTICLE")
public class ArticleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Long id;
	private String imageUrl;
	private String description;
	private Date gmtCreate;
	private Date gmtModified;
	private Long creator;
	private Long viewCnt;
	private Integer likeCnt;
	private Integer commentCnt;
	private String title;
	private Long deptId;
	private String tag;
	private String context;
	private String author;


}
