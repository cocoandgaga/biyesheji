package com.zxj.finalexam.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.Query;

import com.zxj.finalexam.dao.DeptUserDao;
import com.zxj.finalexam.entity.DeptUserEntity;
import com.zxj.finalexam.service.DeptUserService;


@Service("deptUserService")
public class DeptUserServiceImpl extends ServiceImpl<DeptUserDao, DeptUserEntity> implements DeptUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DeptUserEntity> page = this.page(
                new Query<DeptUserEntity>().getPage(params),
                new QueryWrapper<DeptUserEntity>()
        );

        return new PageUtils(page);
    }

}