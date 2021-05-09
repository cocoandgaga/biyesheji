package com.zxj.finalexam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.Query;
import com.zxj.finalexam.dao.AttachmentDao;
import com.zxj.finalexam.entity.Attachment;
import com.zxj.finalexam.service.AttachmentService;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service("attachmentService")
public class AttachmentServiceImpl extends ServiceImpl<AttachmentDao, Attachment> implements AttachmentService {
    @Override
    public PageUtils queryPage(Map<String, Object> params, Long deptId) {
        QueryWrapper<Attachment> queryWrapper = new QueryWrapper<>();
        if (deptId!=null){
            queryWrapper.eq("DEPT_ID", deptId);
        }
        String value = (String) params.get("value");
        if(value!=null){
            queryWrapper.like("FILE_NAME",value);
        }
        IPage<Attachment> pageEntity = new Query<Attachment>().getPage(params);
        IPage<Attachment> page = this.page(pageEntity,queryWrapper);

        return new PageUtils(page);
    }
}
