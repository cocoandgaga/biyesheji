/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.sys.entity.SysUserTokenEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 系统用户Token
 */
@Mapper
@Component
public interface SysUserTokenDao extends BaseMapper<SysUserTokenEntity> {

    SysUserTokenEntity queryByToken(@Param("token") String token);

    SysUserTokenEntity queryByUserId(@Param("userId") long userId);

    SysUserTokenEntity queryByStuId(@Param("stuId") long stuId);

    void updateByUserId(SysUserTokenEntity tokenEntity);

    void updateByStuId(SysUserTokenEntity tokenEntity);

}
