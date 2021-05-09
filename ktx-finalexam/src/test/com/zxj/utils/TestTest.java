package com.zxj.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zxj.common.utils.Constant;
import com.zxj.finalexam.entity.Comment;
import com.zxj.finalexam.service.CommentService;
import com.zxj.finalexam.utils.TreeUtils;
import com.zxj.finalexam.utils.constant.Constants;
import com.zxj.finalexam.vo.CommentVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TestTest {

    CommentService commentService;
    @Test
    public void test() {

        Comment parentComment=new Comment();
        parentComment.setId(1L).setParentId(0L)
                .setType(1).setContent("dfadfas").setCommentator(1L)
                .setLikeCnt(100L).setCommentCnt(2L);
        Comment child1=new Comment();
        child1.setId(2L).setParentId(1L)
                .setType(2).setContent("fasdfdfasdf").setCommentator(1L)
                .setLikeCnt(200L).setCommentCnt(0L);
        Comment child2=new Comment();
        child2.setId(3L).setParentId(1L)
                .setType(2).setContent("adsfhdd").setCommentator(1L)
                .setLikeCnt(300L).setCommentCnt(0L);

        List<Comment> commentList = new ArrayList<>();
        commentList.add(parentComment);
        commentList.add(child1);
        commentList.add(child2);
        TreeUtils.buildTree(commentList,1);

    }
}
