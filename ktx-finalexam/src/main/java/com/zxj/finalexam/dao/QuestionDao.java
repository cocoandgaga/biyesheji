package com.zxj.finalexam.dao;

import com.zxj.finalexam.entity.QuestionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-02-18 19:52:00
 */
@Mapper
public interface QuestionDao extends BaseMapper<QuestionEntity> {

    void subCommentCnt(Long id);

    void addLikeCnt(Long id);

    void subLikeCnt(Long id);

    void addCommentCnt(Long id);
}
