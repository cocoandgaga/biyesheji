package com.zxj.finalexam.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.Query;

import com.zxj.finalexam.dao.CategoryDao;
import com.zxj.finalexam.entity.CategoryEntity;
import com.zxj.finalexam.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>().eq("show_status",1)
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> levelList() {
        List<CategoryEntity> menus=baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("show_status",1));
        List<CategoryEntity> menus_with_children=menus.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid()==0)
                .map(menu-> {
                    menu.setChildren(getChildren(menu,menus));
                    return menu;
                })
                .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort())))
                .collect(Collectors.toList());
        return menus_with_children;
    }

    @Override
    public List<CategoryEntity> levelList2() {
        List<CategoryEntity> menus=baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("show_status",1));
        List<CategoryEntity> menus_with_children=menus.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid()==0)
                .map(menu-> {
                    menu.setChildren(getChildren2(menu,menus));
                    return menu;
                })
                .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort())))
                .collect(Collectors.toList());
        return menus_with_children;
    }

    @Override
    public List<CategoryEntity> getSubjects(Long deptId) {
        if (deptId!=null){
            return this.getBaseMapper().selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", deptId));
        }else {
            return Collections.emptyList();
        }
    }


    private List<CategoryEntity> getChildren2(CategoryEntity parent,List<CategoryEntity> all_menus) {

        List<CategoryEntity> children = all_menus.stream().filter(cur -> cur.getParentCid() == parent.getCatId())
                .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort())))
                .collect(Collectors.toList());
        return children;

    }
    @Override
    public boolean removeMenuByIds(List<Long> catIds) {
        //TODO 菜单的级联 用到菜单的地方就不能删除该菜单
        if (CollectionUtils.isEmpty(catIds)) {
            return false;
        }
        return SqlHelper.retBool(getBaseMapper().deleteBatchIds(catIds));
    }

    @Override
    public List<Long> getdeptCase(Long catId) {
        List<Long> catIds=new ArrayList<>();
         while (catId!=null&&catId!=0) {
             catIds.add(0,catId);
             CategoryEntity categoryEntity = baseMapper.selectOne(
                     new QueryWrapper<CategoryEntity>().eq("cat_id", catId)
                             .eq("show_status",1));

             catId = categoryEntity.getParentCid();
         }
        return catIds;
    }


    private List<CategoryEntity> getChildren(CategoryEntity parent,List<CategoryEntity> all_menus) {

        List<CategoryEntity> children = all_menus.stream().filter(cur -> cur.getParentCid() == parent.getCatId())
                .map(cur -> {
                    cur.setChildren(getChildren(cur, all_menus));
                    return cur;
                })
                .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort())))
                .collect(Collectors.toList());
        return children;

    }
}