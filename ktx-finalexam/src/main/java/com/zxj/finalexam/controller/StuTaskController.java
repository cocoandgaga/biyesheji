package com.zxj.finalexam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zxj.common.utils.R;
import com.zxj.finalexam.dao.FreeColumnItemDao;
import com.zxj.finalexam.dao.StuTaskDao;
import com.zxj.finalexam.entity.FreeColumnItem;
import com.zxj.finalexam.entity.StuTaskEntity;
import com.zxj.finalexam.entity.StuUserEntity;
import com.zxj.finalexam.service.StuTaskService;
import com.zxj.finalexam.service.UserTaskService;
import com.zxj.finalexam.vo.TaskCard;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-04-12 19:44:13
 */
@RestController
@RequestMapping("finalexam/stutask")
public class StuTaskController {
    @Autowired
    private StuTaskService stuTaskService;
    @Autowired
    private FreeColumnItemDao freeColumnItemDao;
    @Autowired
    private UserTaskService userTaskService;
    @Autowired
    private StuTaskDao stuTaskDao;

    /**
     * 信息
     */
    @RequestMapping("/info/{taskId}")
    public R info(@PathVariable("taskId") String taskId) {
        StuTaskEntity stuTask = stuTaskService.getById(taskId);

        return R.ok().put("stuTask", stuTask);
    }

    /**
     * 保存学生卡片任务
     */
    private String validate(StuTaskEntity taskItem) {
        if (StringUtils.isEmpty(taskItem.getStuTaskName())) {
            return "请输入任务名称";
        }
        if (StringUtils.isEmpty(taskItem.getStuTaskDescription())) {
            return "请输入任务描述";
        }
        if (StringUtils.isEmpty(taskItem.getUserTaskId())) {
            return "所属老师发布的卡片不能为空";
        }
        return null;
    }

    @Transactional
    @RequestMapping("/addTaskCard")
    public R save(@RequestBody StuTaskEntity stuTask, HttpServletRequest request) {
        if (StringUtils.isNotEmpty(validate(stuTask))) {
            return R.error(validate(stuTask));
        }
        StuUserEntity stu = (StuUserEntity) request.getSession().getAttribute("user");
        if (stu == null) return R.error("请登录");
        userTaskService.validateUserStoryObjStatus(stuTask.getUserTaskId());
        int count = stuTaskService.count(new QueryWrapper<StuTaskEntity>().eq("USER_TASK_ID", stuTask.getUserTaskId()));
        stuTask.setStuId(stu.getUserId());
        stuTask.setStuName(stu.getNickName());
        stuTask.setDeptId(stu.getDeptId());
        stuTaskService.save(stuTask);
        FreeColumnItem freeColumnItem = new FreeColumnItem();
        freeColumnItem.setColumnId("2");
        freeColumnItem.setType(2);
        freeColumnItem.setSort(count + 1);
        freeColumnItem.setStatusColumnId(3);
        freeColumnItem.setStuTaskId(stuTask.getStuTaskId());
        freeColumnItem.setUserTaskId(stuTask.getUserTaskId());
        freeColumnItemDao.insert(freeColumnItem);
        TaskCard taskCard = stuTaskDao.getTaskAndPosition(stuTask.getStuTaskId());
        return R.ok().put("stuTask", taskCard);
    }

    @Transactional
    @RequestMapping("/editTaskCard")
    public R edit(@RequestBody StuTaskEntity stuTask, HttpServletRequest request) {
        if (StringUtils.isNotEmpty(validate(stuTask))) {
            return R.error(validate(stuTask));
        }
        StuUserEntity stu = (StuUserEntity) request.getSession().getAttribute("user");
        if (stu == null) return R.error("请登录");
        userTaskService.validateUserStoryObjStatus(stuTask.getUserTaskId());
        stuTaskService.updateById(stuTask);
        TaskCard taskCard = stuTaskDao.getTaskAndPosition(stuTask.getStuTaskId());
        return R.ok().put("stuTask", taskCard);
    }


    /**
     * 删除卡片,删除卡片下任务
     */
    @GetMapping("/deleteTaskCard/{userTaskId}/{stuTaskId}")
    public R removeCardItem(@PathVariable String userTaskId, @PathVariable String stuTaskId, HttpServletRequest request) {
        StuUserEntity stu = (StuUserEntity) request.getSession().getAttribute("user");
        if (stu == null) return R.error("请登录");
        StuTaskEntity stuTaskEntity = stuTaskService.getById(stuTaskId);
        if (!stuTaskEntity.getStuId().equals(stu.getUserId())) {
            return R.error("无操作权限");
        } else {
            userTaskService.validateUserStoryObjStatus(userTaskId);
            stuTaskDao.removeTaskFreeColumnMessagesById(stuTaskId);
            return R.ok();
        }
    }
}
