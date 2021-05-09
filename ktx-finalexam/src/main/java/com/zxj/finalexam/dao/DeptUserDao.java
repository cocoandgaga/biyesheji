package com.zxj.finalexam.dao;

import com.zxj.finalexam.entity.DeptUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-04-03 19:16:26
 */
@Mapper
public interface DeptUserDao extends BaseMapper<DeptUserEntity> {

    DeptUserEntity getDeptName(@Param("userId") Long userId);
}
