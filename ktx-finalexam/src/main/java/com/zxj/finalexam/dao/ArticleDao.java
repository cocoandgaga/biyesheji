package com.zxj.finalexam.dao;

import com.zxj.finalexam.entity.ArticleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-02-18 19:51:59
 */
@Mapper
public interface ArticleDao extends BaseMapper<ArticleEntity> {
     void updateViewCnt(ArticleEntity articleEntity);
    void updateLikeCnt(ArticleEntity articleEntity);
    void updateCommentCnt(ArticleEntity articleEntity);

}
