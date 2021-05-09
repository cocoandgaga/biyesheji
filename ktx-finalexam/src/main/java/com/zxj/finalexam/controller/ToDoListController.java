package com.zxj.finalexam.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zxj.finalexam.entity.ToDoListEntity;
import com.zxj.finalexam.service.ToDoListService;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.R;



/**
 * 复习清单
 *
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-03-10 00:50:13
 */
@RestController
@RequestMapping("finalexam/todolist")
public class ToDoListController {
    @Autowired
    private ToDoListService toDoListService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = toDoListService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		ToDoListEntity toDoList = toDoListService.getById(id);

        return R.ok().put("toDoList", toDoList);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ToDoListEntity toDoList){
		toDoListService.save(toDoList);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ToDoListEntity toDoList){
		toDoListService.updateById(toDoList);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		toDoListService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
