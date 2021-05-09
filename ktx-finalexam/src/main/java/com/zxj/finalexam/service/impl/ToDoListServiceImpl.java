package com.zxj.finalexam.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.Query;

import com.zxj.finalexam.dao.ToDoListDao;
import com.zxj.finalexam.entity.ToDoListEntity;
import com.zxj.finalexam.service.ToDoListService;


@Service("toDoListService")
public class ToDoListServiceImpl extends ServiceImpl<ToDoListDao, ToDoListEntity> implements ToDoListService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ToDoListEntity> page = this.page(
                new Query<ToDoListEntity>().getPage(params),
                new QueryWrapper<ToDoListEntity>()
        );

        return new PageUtils(page);
    }

}