package com.zxj.finalexam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.Query;
import com.zxj.common.utils.RRException;
import com.zxj.finalexam.dao.UserTaskDao;
import com.zxj.finalexam.entity.UserTaskEntity;
import com.zxj.finalexam.service.UserTaskService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("userTaskService")
public class UserTaskServiceImpl extends ServiceImpl<UserTaskDao, UserTaskEntity> implements UserTaskService {

    @Autowired
    private UserTaskDao userTaskDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params,Long userId) {
        String key = (String) params.get("key");
        QueryWrapper<UserTaskEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("USER_ID",userId);
        if(key!=null&&!key.isEmpty()){
            queryWrapper.eq("USER_TASK_NAME",key);
        }

        IPage<UserTaskEntity> page = this.page(
                new Query<UserTaskEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public UserTaskEntity validateUserStoryObjStatus(String userTaskId) {
        if (StringUtils.isBlank(userTaskId)) {
            throw new RRException("所属老师任务卡片不能为空");
        }
        UserTaskEntity userTask = userTaskDao.selectById(userTaskId);
        if (userTask == null || userTask.getObjStatus()==1) {
            throw new RRException("该老师发布的任务不存在或已被删除,请刷新页面");
        }
        return userTask;
    }


}