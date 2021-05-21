package com.zxj.finalexam.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.Query;

import com.zxj.finalexam.dao.CourseScoreDao;
import com.zxj.finalexam.entity.CourseScoreEntity;
import com.zxj.finalexam.service.CourseScoreService;


@Service("courseScoreService")
public class CourseScoreServiceImpl extends ServiceImpl<CourseScoreDao, CourseScoreEntity> implements CourseScoreService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CourseScoreEntity> page = this.page(
                new Query<CourseScoreEntity>().getPage(params),
                new QueryWrapper<CourseScoreEntity>()
        );

        return new PageUtils(page);
    }

}