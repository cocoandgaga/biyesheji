package com.zxj.finalexam.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.Query;

import com.zxj.finalexam.dao.ReviewMaterialsDao;
import com.zxj.finalexam.entity.ReviewMaterialsEntity;
import com.zxj.finalexam.service.ReviewMaterialsService;


@Service("reviewMaterialsService")
public class ReviewMaterialsServiceImpl extends ServiceImpl<ReviewMaterialsDao, ReviewMaterialsEntity> implements ReviewMaterialsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ReviewMaterialsEntity> page = this.page(
                new Query<ReviewMaterialsEntity>().getPage(params),
                new QueryWrapper<ReviewMaterialsEntity>()
        );

        return new PageUtils(page);
    }

}