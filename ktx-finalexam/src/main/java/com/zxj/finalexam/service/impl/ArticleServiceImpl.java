package com.zxj.finalexam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.Query;
import com.zxj.finalexam.dao.ArticleDao;
import com.zxj.finalexam.entity.ArticleEntity;
import com.zxj.finalexam.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, ArticleEntity> implements ArticleService {


    @Override
    public PageUtils queryPage(Map<String, Object> params,Long deptId) {
        QueryWrapper<ArticleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("ID","IMAGE_URL", "DESCRIPTION","GMT_CREATE","GMT_MODIFIED"
                ,"CREATOR","VIEW_CNT","LIKE_CNT","COMMENT_CNT","TITLE","TAG");
        if (deptId!=null){
            queryWrapper.eq("DEPT_ID", deptId);
        }
        String key = (String)params.get("key");
        if(key!=null&&!key.isEmpty()){
            queryWrapper.like("TITLE", key);
        }
        IPage<ArticleEntity> pageEntity = new Query<ArticleEntity>().getPage(params);
        IPage<ArticleEntity> page = this.page(pageEntity,queryWrapper);

        return new PageUtils(page);
    }

}