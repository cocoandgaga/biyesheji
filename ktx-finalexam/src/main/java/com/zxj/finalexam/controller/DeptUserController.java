package com.zxj.finalexam.controller;

import  java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zxj.finalexam.entity.DeptUserEntity;
import com.zxj.finalexam.service.DeptUserService;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.R;



/**
 * 
 *
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-04-03 19:16:26
 */
@RestController
@RequestMapping("finalexam/deptuser")
public class DeptUserController {
    @Autowired
    private DeptUserService deptUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = deptUserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		DeptUserEntity deptUser = deptUserService.getById(id);

        return R.ok().put("deptUser", deptUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody DeptUserEntity deptUser){
		deptUserService.save(deptUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody DeptUserEntity deptUser){
		deptUserService.updateById(deptUser);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		deptUserService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
