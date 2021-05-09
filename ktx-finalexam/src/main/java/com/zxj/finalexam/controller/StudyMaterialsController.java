package com.zxj.finalexam.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zxj.finalexam.entity.StudyMaterialsEntity;
import com.zxj.finalexam.service.StudyMaterialsService;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.R;

/**
 * 
 *
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-02-18 19:52:00
 */
@RestController
@RequestMapping("finalexam/studymaterials")
public class StudyMaterialsController {
    @Autowired
    private StudyMaterialsService studyMaterialsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = studyMaterialsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		StudyMaterialsEntity studyMaterials = studyMaterialsService.getById(id);

        return R.ok().put("studyMaterials", studyMaterials);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody StudyMaterialsEntity studyMaterials){
		studyMaterialsService.save(studyMaterials);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody StudyMaterialsEntity studyMaterials){
		studyMaterialsService.updateById(studyMaterials);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		studyMaterialsService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
