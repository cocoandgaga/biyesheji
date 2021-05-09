package com.zxj.finalexam.controller;

import com.baomidou.mybatisplus.core.assist.ISqlRunner;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.R;
import com.zxj.finalexam.dao.DeptUserDao;
import com.zxj.finalexam.dao.FreeColumnItemDao;
import com.zxj.finalexam.entity.*;
import com.zxj.finalexam.service.DeptUserService;
import com.zxj.finalexam.service.StuTaskService;
import com.zxj.finalexam.service.StuUserService;
import com.zxj.finalexam.service.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 
 *
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
    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,HttpServletRequest request){
        Long userId = (Long) request.getSession().getAttribute("creator");
        if (userId ==null) return R.error("请登录");
        PageUtils page = userTaskService.queryPage(params,userId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userTaskId}")
    public R info(@PathVariable("userTaskId") String userTaskId){
		UserTaskEntity userTask = userTaskService.getById(userTaskId);

        return R.ok().put("userTask", userTask);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody UserTaskEntity userTask, HttpServletRequest request){
        Long user_id=(Long)request.getSession().getAttribute("creator");
        DeptUserEntity deptUser = deptUserDao.getDeptName(user_id);
        userTask.setDeptId(deptUser.getDeptId());
        userTask.setUserId(user_id);
        userTask.setUserName(deptUser.getUsername());
        userTaskService.save(userTask);
        FreeColumnItem freeColumnItem=new FreeColumnItem();
        freeColumnItem.setColumnId("1");
        freeColumnItem.setColumnTypeSort(1);
        freeColumnItem.setSort(1);
        freeColumnItem.setStatusColumnId(1);
        freeColumnItem.setType(1);
        freeColumnItem.setUserTaskId(userTask.getUserTaskId());
        freeColumnItemDao.insert(freeColumnItem);
      /*  List<StuUserEntity> stuList = stuUserService.list(new QueryWrapper<StuUserEntity>().eq("CREATE_USER_ID", user_id).eq("OBJ_STATUS",0));
        stuList.stream().forEach(stuUserEntity -> {
            StuTaskEntity stuTaskEntity=new StuTaskEntity();
            stuTaskEntity.setDeptId(deptUser.getDeptId());
            stuTaskEntity.setStartTime(userTask.getStartTime());
            stuTaskEntity.setEndTime(userTask.getEndTime());
            stuTaskEntity.setStuId(stuUserEntity.getUserId());
            stuTaskEntity.setStuName(stuUserEntity.getNickName());
            stuTaskEntity.setStuTaskName(userTask.getUserTaskName());
            stuTaskEntity.setStuTaskDescription(userTask.getUserTaskDescription());
            stuTaskEntity.setUserTaskId(userTask.getUserTaskId());
            stuTaskEntity.setSubjectName(userTask.getSubjectName());
            stuTaskService.save(stuTaskEntity);
        });
*/
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody UserTaskEntity userTask){
		userTaskService.updateById(userTask);
        QueryWrapper<FreeColumnItem> queryWrapper = new QueryWrapper<FreeColumnItem>().eq("user_task_id", userTask.getUserTaskId())
                .eq("type", 1);
        FreeColumnItem freeColumnItem = new FreeColumnItem();
        freeColumnItem.setStatusColumnId(userTask.getUserTaskStatus());
        freeColumnItemDao.update(freeColumnItem,queryWrapper);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] userTaskIds){
		userTaskService.removeByIds(Arrays.asList(userTaskIds));

        return R.ok();
    }

}
