package com.zxj.finalexam.controller;

import java.util.Arrays;
import java.util.Map;

import com.zxj.finalexam.dao.DeptUserDao;
import com.zxj.finalexam.entity.DeptUserEntity;
import com.zxj.finalexam.entity.StuUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zxj.finalexam.entity.ExamTimeEntity;
import com.zxj.finalexam.service.ExamTimeService;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.R;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 *
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-05-15 10:02:03
 */
@RestController
@RequestMapping("finalexam/examtime")
public class ExamTimeController {
    @Autowired
    private ExamTimeService examTimeService;
    @Autowired
    private DeptUserDao deptUserDao;
    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        Long creator = (Long) request.getSession().getAttribute("creator");
        DeptUserEntity dept = deptUserDao.getDeptName(creator);
        StuUserEntity user = (StuUserEntity) request.getSession().getAttribute("user");
        Long deptId = null;
        if(creator!=null) deptId= dept.getDeptId();
        if (user!=null) deptId=user.getDeptId();

        PageUtils page = examTimeService.queryPage(params, deptId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		ExamTimeEntity examTime = examTimeService.getById(id);
        return R.ok().put("examTime", examTime);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ExamTimeEntity examTime,HttpServletRequest request){
        Long creator = (Long) request.getSession().getAttribute("creator");
        DeptUserEntity dept = deptUserDao.getDeptName(creator);
        examTime.setDeptId(dept.getDeptId());
		examTimeService.save(examTime);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ExamTimeEntity examTime){

		examTimeService.updateById(examTime);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		examTimeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
