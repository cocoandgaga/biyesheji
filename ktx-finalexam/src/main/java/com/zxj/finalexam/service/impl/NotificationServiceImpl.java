package com.zxj.finalexam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.Query;
import com.zxj.finalexam.dao.NotificationDao;
import com.zxj.finalexam.entity.Notification;
import com.zxj.finalexam.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("notificationService")
public class NotificationServiceImpl extends ServiceImpl<NotificationDao, Notification> implements NotificationService {
    @Override
    public PageUtils queryPage(Map<String, Object> params,Long userId,Integer status) {

        QueryWrapper<Notification> queryWrapper = new QueryWrapper<Notification>().eq("RECEIVER", userId);
        if (status!=null){
            queryWrapper.eq("STATUS",status);
        }
        IPage<Notification> page = this.page(
                new Query<Notification>().getPage(params),
                queryWrapper.orderBy(true,false,"GMT_CREATE")
        );

        return new PageUtils(page);
    }
}
