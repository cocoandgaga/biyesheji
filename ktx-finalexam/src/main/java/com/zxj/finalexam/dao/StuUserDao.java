package com.zxj.finalexam.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxj.finalexam.entity.StuUserEntity;
import com.zxj.finalexam.entity.SysUserTokenEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 前台用户
 *
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-03-25 00:27:07
 */
@Mapper
public interface StuUserDao extends BaseMapper<StuUserEntity> {
    StuUserEntity queryByUserId(@Param("userId") Long userId);

    void updateToken(SysUserTokenEntity tokenEntity);
}
