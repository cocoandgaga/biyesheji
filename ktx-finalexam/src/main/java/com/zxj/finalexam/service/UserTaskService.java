package com.zxj.finalexam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxj.common.utils.PageUtils;
import com.zxj.finalexam.entity.UserTaskEntity;
import com.zxj.finalexam.vo.BoardVo;
import com.zxj.finalexam.vo.UserStoryCard;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-04-12 19:44:13
 */
public interface UserTaskService extends IService<UserTaskEntity> {

    PageUtils queryPage(Map<String, Object> params,Long userId);

    UserTaskEntity validateUserStoryObjStatus(String userTaskId);
}

