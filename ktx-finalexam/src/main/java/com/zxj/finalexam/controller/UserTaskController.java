package com.zxj.finalexam.controller;

import com.baomidou.mybatisplus.core.assist.ISqlRunner;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.R;
import com.zxj.finalexam.dao.CompleteStatusDao;
import com.zxj.finalexam.dao.DeptUserDao;
import com.zxj.finalexam.dao.FreeColumnItemDao;
import com.zxj.finalexam.dto.CompleteStatusDTO;
import com.zxj.finalexam.entity.*;
import com.zxj.finalexam.service.DeptUserService;
import com.zxj.finalexam.service.StuTaskService;
import com.zxj.finalexam.service.StuUserService;
import com.zxj.finalexam.service.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-04-12 19:44:13
 */
@RestController
@RequestMapping("finalexam/usertask")
public class UserTaskController {
    @Autowired
    FreeColumnItemDao freeColumnItemDao;
    @Autowired
    private UserTaskService userTaskService;
    @Autowired
    private DeptUserDao deptUserDao;
    @Autowired
    private StuTaskService stuTaskService;
    @Autowired
    private StuUserService stuUserService;
    @Autowired
    private CompleteStatusDao completeStatusDao;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("creator");
        if (userId == null) return R.error("请登录");
        PageUtils page = userTaskService.queryPage(params, userId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userTaskId}")
    public R info(@PathVariable("userTaskId") String userTaskId) {
        UserTaskEntity userTask = userTaskService.getById(userTaskId);

        return R.ok().put("userTask", userTask);
    }

    @RequestMapping("/updateCompleteStatus/{userTaskId}/{status}")
    @Transactional
    public R updateCompleteStatus(@PathVariable("userTaskId") String userTaskId,
                                  @PathVariable("status") Integer status,
                                  HttpServletRequest request) {
        StuUserEntity user = (StuUserEntity) request.getSession().getAttribute("user");
        if (user == null) return R.error("请登录");

        CompleteStatus completeStatus = completeStatusDao.selectOne(new QueryWrapper<CompleteStatus>().
                eq("USER_TASK_ID", userTaskId).eq("STU_ID", user.getUserId()));
        if (completeStatus != null) {
            completeStatus.setStatus(status);
            completeStatusDao.updateById(completeStatus);
        } else {
            completeStatus = new CompleteStatus();
            completeStatus.setUserTaskId(userTaskId);
            completeStatus.setGmtCreate(new Date());
            completeStatus.setStuId(user.getUserId());
            completeStatus.setStatus(status);
            completeStatusDao.insert(completeStatus);
        }
        return R.ok();
    }


    @RequestMapping("/getCompleteStatus/{userTaskId}")
    public R getCompleteStatus(@PathVariable("userTaskId") String userTaskId, HttpServletRequest request) {
        Long creator = (Long) request.getSession().getAttribute("creator");
        StuUserEntity user = (StuUserEntity) request.getSession().getAttribute("user");

        List<CompleteStatusDTO> uncompleted = new ArrayList<>();
        List<CompleteStatusDTO> completed = new ArrayList<>();
        List<CompleteStatusDTO> list=null;
        if (creator!=null) {
            DeptUserEntity deptUser = deptUserDao.getDeptName(creator);
            list = completeStatusDao.getCompleted(userTaskId, deptUser.getDeptId());
        }else if (user!=null){
            list = completeStatusDao.getCompleted(userTaskId, user.getDeptId());
        }
        for (CompleteStatusDTO dto : list) {
            if (dto.getStatus() == 0 || dto.getStatus() == 2)
                uncompleted.add(dto);
            else
                completed.add(dto);
        }

        return Objects.requireNonNull(R.ok().put("completed", completed)).put("uncompleted", uncompleted).put("completedCnt",completed.size()).put("uncompletedCnt",uncompleted.size());
    }


    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody UserTaskEntity userTask, HttpServletRequest request) {
        Long user_id = (Long) request.getSession().getAttribute("creator");
        DeptUserEntity deptUser = deptUserDao.getDeptName(user_id);
        userTask.setDeptId(deptUser.getDeptId());
        userTask.setUserId(user_id);
        userTask.setUserName(deptUser.getUsername());
        userTaskService.save(userTask);
        FreeColumnItem freeColumnItem = new FreeColumnItem();
        freeColumnItem.setColumnId("1");
        freeColumnItem.setColumnTypeSort(1);
        freeColumnItem.setSort(1);
        freeColumnItem.setStatusColumnId(1);
        freeColumnItem.setType(1);
        freeColumnItem.setUserTaskId(userTask.getUserTaskId());
        freeColumnItemDao.insert(freeColumnItem);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody UserTaskEntity userTask) {
        userTaskService.updateById(userTask);
        QueryWrapper<FreeColumnItem> queryWrapper = new QueryWrapper<FreeColumnItem>().eq("user_task_id", userTask.getUserTaskId())
                .eq("type", 1);
        FreeColumnItem freeColumnItem = new FreeColumnItem();
        freeColumnItem.setStatusColumnId(userTask.getUserTaskStatus());
        freeColumnItemDao.update(freeColumnItem, queryWrapper);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] userTaskIds) {
        userTaskService.removeByIds(Arrays.asList(userTaskIds));

        return R.ok();
    }

}
