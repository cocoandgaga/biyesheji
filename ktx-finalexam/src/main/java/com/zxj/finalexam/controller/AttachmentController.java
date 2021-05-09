package com.zxj.finalexam.controller;

import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.R;
import com.zxj.finalexam.dao.DeptUserDao;
import com.zxj.finalexam.entity.ArticleEntity;
import com.zxj.finalexam.entity.Attachment;
import com.zxj.finalexam.entity.DeptUserEntity;
import com.zxj.finalexam.entity.StuUserEntity;
import com.zxj.finalexam.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("finalexam/attachment")
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private DeptUserDao deptUserDao;

    @RequestMapping("/get")
    public R get(@RequestParam Map<String, Object> params, HttpServletRequest request){
        StuUserEntity user = (StuUserEntity) request.getSession().getAttribute("user");
        Long creator = (Long) request.getSession().getAttribute("creator");
        if (user!=null) {
            PageUtils page = attachmentService.queryPage(params, user.getDeptId());
            return R.ok().put("page", page);
        }else if (creator!=null){
            DeptUserEntity deptUserEntity = deptUserDao.getDeptName(creator);
            PageUtils page = attachmentService.queryPage(params, deptUserEntity.getDeptId());
            return R.ok().put("page", page);
        }
        else{
            return R.error("请登录..");
        }
    }

    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        Attachment attachment = attachmentService.getById(id);
        return R.ok().put("attachment", attachment);
    }

    @RequestMapping("/delete")
    public R delete(@RequestBody String[] ids){
        attachmentService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
}
