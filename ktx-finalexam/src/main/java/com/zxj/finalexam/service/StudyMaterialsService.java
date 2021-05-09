package com.zxj.finalexam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxj.common.utils.PageUtils;
import com.zxj.finalexam.entity.StudyMaterialsEntity;

import java.util.Map;

/**
 * 
 *
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-02-18 19:52:00
 */
public interface StudyMaterialsService extends IService<StudyMaterialsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

