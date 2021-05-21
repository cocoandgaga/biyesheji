package com.zxj.finalexam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxj.common.utils.PageUtils;
import com.zxj.finalexam.entity.CourseScoreEntity;

import java.util.Map;

/**
 * 
 *
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-05-16 23:04:59
 */
public interface CourseScoreService extends IService<CourseScoreEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

