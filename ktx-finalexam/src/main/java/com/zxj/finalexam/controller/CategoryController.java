package com.zxj.finalexam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.R;
import com.zxj.finalexam.entity.CategoryEntity;
import com.zxj.finalexam.entity.DeptUserEntity;
import com.zxj.finalexam.entity.StuUserEntity;
import com.zxj.finalexam.service.CategoryService;
import com.zxj.finalexam.service.DeptUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 
 *
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-02-18 19:52:00
 */
@RestController
@RequestMapping("finalexam/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DeptUserService deptUserService;

    @RequestMapping("/level/list")
    public R listWithTree(){
        List<CategoryEntity> levels=categoryService.levelList();
        return  R.ok().put("menus",levels);
    }
    @RequestMapping("/level/list2")
    public R listWithTree2(){
        List<CategoryEntity> levels=categoryService.levelList2();
        return  R.ok().put("menus",levels);
    }

    @RequestMapping("/level/subjects")
    public R listSubject(HttpServletRequest request){
        Long creator = (Long) request.getSession().getAttribute("creator");
        StuUserEntity user = (StuUserEntity) request.getSession().getAttribute("user");
        DeptUserEntity deptUser=null;
        List<CategoryEntity> categoryEntities=null;
        if(creator!=null) {
             deptUser = deptUserService.getOne(new QueryWrapper<DeptUserEntity>().eq("user_id", creator));
             categoryEntities = categoryService.getSubjects(deptUser.getDeptId());
        }
        else{
            categoryEntities=categoryService.getSubjects(user.getDeptId());
        }

        return  R.ok().put("subjects",categoryEntities);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("category", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }
    /**
     * 拖拽的保存
     */
    @RequestMapping("/update/dragging")
    public R saveDragging(@RequestBody CategoryEntity[] category){
        categoryService.updateBatchById(Arrays.asList(category));

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateById(category);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] catIds){
        categoryService.removeMenuByIds(Arrays.asList(catIds));
        return R.ok();
    }

}
