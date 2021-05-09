package com.zxj.finalexam.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.Query;
import com.zxj.finalexam.dao.QuestionDao;
import com.zxj.finalexam.entity.QuestionEntity;
import com.zxj.finalexam.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;


@Service("questionService")
public class QuestionServiceImpl extends ServiceImpl<QuestionDao, QuestionEntity> implements QuestionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<QuestionEntity> queryWrapper=new QueryWrapper<>();
        if (params.get("value")!=null&&!params.get("value").toString().isEmpty()){
           queryWrapper = new QueryWrapper<QuestionEntity>().like((String) params.get("key"), params.get("value"));
        }
        if(params.get("beginTime")!=null&&!params.get("beginTime").toString().isEmpty()&&params.get("endTime")!=null&&!params.get("endTime").toString().isEmpty()){
            Date beginTime = JSON.parseObject(JSON.toJSONString(params.get("beginTime")), Date.class);
            Date endTime = JSON.parseObject(JSON.toJSONString(params.get("endTime")), Date.class);
            queryWrapper.between("GMT_CREATE",beginTime,endTime);
        }
        System.out.println("////////////"+params.get("type"));
        if (params.get("type")!=null&&!params.get("type").toString().isEmpty()){
            queryWrapper.eq("TYPE",params.get("type"));
        }
        IPage<QuestionEntity> page = this.page(
                new Query<QuestionEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }



}