package com.zxj.finalexam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxj.common.utils.PageUtils;
import com.zxj.finalexam.entity.DeptUserEntity;

import java.util.Map;

/**
 * 
 *
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-04-03 19:16:26
 */
public interface DeptUserService extends IService<DeptUserEntity> {

    PageUtils queryPage(Map<String, Object> params);

}

