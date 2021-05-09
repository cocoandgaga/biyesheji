package com.zxj.finalexam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxj.common.utils.PageUtils;
import com.zxj.finalexam.entity.Attachment;

import java.util.Map;

public interface AttachmentService  extends IService<Attachment> {
    PageUtils queryPage(Map<String, Object> params, Long deptId);
}
