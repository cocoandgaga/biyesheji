package com.zxj.finalexam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxj.common.utils.PageUtils;
import com.zxj.finalexam.entity.ExamTimeEntity;

import java.util.Map;

/**
 * 
 *
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-05-15 10:02:03
 */
public interface ExamTimeService extends IService<ExamTimeEntity> {

    PageUtils queryPage(Map<String, Object> params,Long deptId);
}

