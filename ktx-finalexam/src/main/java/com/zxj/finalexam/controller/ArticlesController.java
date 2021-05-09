package com.zxj.finalexam.controller;

import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.R;
import com.zxj.finalexam.entity.ArticleEntity;
import com.zxj.finalexam.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 
 *
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-02-18 19:51:59
 */
@RestController
@RequestMapping("finalexam/articles")
public class ArticlesController {
    @Autowired
    private ArticleService articleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = articleService.queryPage(params,1L);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ArticleEntity articlesEntity = articleService.getById(id);
        return R.ok().put("article", articlesEntity);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ArticleEntity articleEntity){
        articleService.save(articleEntity);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ArticleEntity reviewMaterials){
        articleService.updateById(reviewMaterials);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        articleService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
