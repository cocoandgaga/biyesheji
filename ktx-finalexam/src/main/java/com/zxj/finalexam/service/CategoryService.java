package com.zxj.finalexam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxj.common.utils.PageUtils;
import com.zxj.finalexam.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-02-18 19:52:00
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> levelList();

    boolean removeMenuByIds(List<Long> catIds);

    List<Long> getdeptCase(Long catId);

    List<CategoryEntity> levelList2();

    List<CategoryEntity> getSubjects(Long deptId);
}

