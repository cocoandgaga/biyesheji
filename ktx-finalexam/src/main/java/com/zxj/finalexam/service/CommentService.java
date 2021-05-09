package com.zxj.finalexam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxj.common.utils.PageUtils;
import com.zxj.finalexam.entity.Comment;

import java.util.Map;


public interface CommentService extends IService<Comment> {
    PageUtils queryPage(Map<String, Object> params);
}
