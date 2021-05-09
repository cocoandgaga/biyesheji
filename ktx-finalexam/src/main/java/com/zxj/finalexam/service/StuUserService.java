package com.zxj.finalexam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxj.common.utils.PageUtils;
import com.zxj.finalexam.entity.StuUserEntity;

import java.util.Map;

/**
 * 前台用户
 *
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-03-25 00:27:07
 */
public interface StuUserService extends IService<StuUserEntity> {

    PageUtils queryPage(Map<String, Object> params,Long deptId);

    StuUserEntity queryByUserName(String username);

    void saveUser(StuUserEntity stuUser);


    boolean updatePassword(Long userId, String password, String newPassword);
}

