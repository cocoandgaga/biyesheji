package com.zxj.finalexam.dao;

import com.zxj.finalexam.entity.ToDoListEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 复习清单
 * 
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-03-10 00:50:13
 */
@Mapper
public interface ToDoListDao extends BaseMapper<ToDoListEntity> {
	
}
