package com.zxj.finalexam.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.Query;

import com.zxj.finalexam.dao.ExamTimeDao;
import com.zxj.finalexam.entity.ExamTimeEntity;
import com.zxj.finalexam.service.ExamTimeService;


@Service("examTimeService")
public class ExamTimeServiceImpl extends ServiceImpl<ExamTimeDao, ExamTimeEntity> implements ExamTimeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params,Long deptId) {
        QueryWrapper<ExamTimeEntity> queryWrapper = new QueryWrapper<>();
        if (deptId!=null){
            queryWrapper.eq("DEPT_ID",deptId);
        }
        IPage<ExamTimeEntity> page = this.page(
                new Query<ExamTimeEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

}