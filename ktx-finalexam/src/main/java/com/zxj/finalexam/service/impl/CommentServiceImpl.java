package com.zxj.finalexam.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.Query;
import com.zxj.common.utils.RRException;
import com.zxj.finalexam.dao.CommentDao;
import com.zxj.finalexam.entity.Comment;
import com.zxj.finalexam.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Integer type = (Integer) params.get("type");
        Long questionId = (Long) params.get("questionId");
        if (type == null || questionId == null) throw new RRException("问题参数错误");
        IPage<Comment> page = this.page(
                new Query<Comment>().getPage(params),
                new QueryWrapper<Comment>()
                        .eq("TYPE", type)
                        .eq("PARENT_ID", questionId));
        return new PageUtils(page);
    }
}
