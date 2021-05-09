/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.R;
import io.renren.modules.sys.dao.SysUserTokenDao;
import io.renren.modules.sys.entity.SysUserTokenEntity;
import io.renren.modules.sys.oauth2.TokenGenerator;
import io.renren.modules.sys.service.SysUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service("sysUserTokenService")
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserTokenEntity> implements SysUserTokenService {
	//12小时后过期
	private final static int EXPIRE = 3600 * 12;
	@Autowired
	private SysUserTokenDao sysUserTokenDao;
	@Autowired
	private SysUserTokenService sysUserTokenService;

	@Override
	public R createToken(long id,boolean isAdmin) {
		//生成一个token
		String token = TokenGenerator.generateValue();
		//当前时间
		Date now = new Date();
		//过期时间
		Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
		SysUserTokenEntity tokenEntity ;
		if (isAdmin){
			tokenEntity =sysUserTokenDao.queryByUserId(id);
		}else {
			tokenEntity =sysUserTokenDao.queryByStuId(id);
		}
		//判断是否生成过token
		if(tokenEntity == null){
			tokenEntity = new SysUserTokenEntity();
			if (isAdmin)
				tokenEntity.setUserId(id);
			else
				tokenEntity.setStuId(id);
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);
			//保存token
			this.save(tokenEntity);
		}else{
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);
			if (isAdmin) {
				tokenEntity.setUserId(id);
				sysUserTokenDao.updateByUserId(tokenEntity);
			}
			else {
				tokenEntity.setStuId(id);
				sysUserTokenDao.updateByStuId(tokenEntity);
			}
			//更新token

		}
		return R.ok().put("token", token).put("expire", EXPIRE);
	}

	@Override
	public void logout(long userId) {
		//生成一个token
		String token = TokenGenerator.generateValue();

		//修改token
		SysUserTokenEntity tokenEntity = new SysUserTokenEntity();
		tokenEntity.setUserId(userId);
		tokenEntity.setToken(token);
		this.updateById(tokenEntity);
	}
}
