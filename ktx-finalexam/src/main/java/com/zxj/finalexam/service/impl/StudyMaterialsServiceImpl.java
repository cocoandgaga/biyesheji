package com.zxj.finalexam.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.Query;

import com.zxj.finalexam.dao.StudyMaterialsDao;
import com.zxj.finalexam.entity.StudyMaterialsEntity;
import com.zxj.finalexam.service.StudyMaterialsService;


@Service("studyMaterialsService")
public class StudyMaterialsServiceImpl extends ServiceImpl<StudyMaterialsDao, StudyMaterialsEntity> implements StudyMaterialsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StudyMaterialsEntity> page = this.page(
                new Query<StudyMaterialsEntity>().getPage(params),
                new QueryWrapper<StudyMaterialsEntity>()
        );

        return new PageUtils(page);
    }

}