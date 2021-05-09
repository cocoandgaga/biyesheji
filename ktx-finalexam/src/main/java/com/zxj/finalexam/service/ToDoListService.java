package com.zxj.finalexam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxj.common.utils.PageUtils;
import com.zxj.finalexam.entity.ToDoListEntity;

import java.util.Map;

/**
 * 复习清单
 *
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-03-10 00:50:13
 */
public interface ToDoListService extends IService<ToDoListEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

