package com.zxj.finalexam.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxj.finalexam.dto.PaginationDTO;
import com.zxj.finalexam.entity.Comment;
import com.zxj.finalexam.vo.CommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentDao extends BaseMapper<Comment> {
    List<CommentVO> queryCommentsByQuestionId(PaginationDTO paginationDTO);
    List<Comment> queryParentCommentsByQuestionId(@Param("questionId")Long questionId);
    void addLikeCnt(Long id);

    void delLikeCnt(Long id);

    void subCommentCnt(Long id);

    void addCommentCnt(Long id);
}
