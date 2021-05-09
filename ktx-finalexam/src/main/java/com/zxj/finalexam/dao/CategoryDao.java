package com.zxj.finalexam.dao;

import com.zxj.finalexam.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxj.finalexam.entity.Comment;
import com.zxj.finalexam.vo.CommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-02-18 19:52:00
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
}
