package com.zxj.finalexam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxj.common.utils.PageUtils;
import com.zxj.finalexam.entity.Notification;

import java.util.Map;

public interface NotificationService  extends IService<Notification> {
    PageUtils queryPage(Map<String, Object> params,Long userId,Integer status);
}
