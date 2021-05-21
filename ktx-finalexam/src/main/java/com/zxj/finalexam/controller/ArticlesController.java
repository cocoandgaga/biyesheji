package com.zxj.finalexam.controller;

import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.R;
import com.zxj.finalexam.dao.ArticleDao;
import com.zxj.finalexam.dao.DeptUserDao;
import com.zxj.finalexam.entity.ArticleEntity;
import com.zxj.finalexam.entity.DeptUserEntity;
import com.zxj.finalexam.entity.StuUserEntity;
import com.zxj.finalexam.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;



/**
 * 
 *
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-02-18 19:51:59
 */
@RestController
@RequestMapping("finalexam/articles")
public class ArticlesController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private DeptUserDao deptUserDao;
    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        Long creator = (Long) request.getSession().getAttribute("creator");
        StuUserEntity user = (StuUserEntity) request.getSession().getAttribute("user");
        if (creator==null&&user==null) return R.error("请登录");
        Long deptId =null;
        if (creator!=null) {
            DeptUserEntity deptUser = deptUserDao.getDeptName(creator);
            deptId = deptUser.getDeptId();
        }
        if (user!=null)
            deptId=user.getDeptId();

        PageUtils page = articleService.queryPage(params, deptId);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info (@PathVariable("id") Long id){
        articleDao.updateViewCnt(id);
        ArticleEntity articlesEntity = articleService.getById(id);
        return R.ok().put("article", articlesEntity);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ArticleEntity articleEntity, HttpServletRequest request){
        Long creator = (Long) request.getSession().getAttribute("creator");
        DeptUserEntity deptUser = deptUserDao.getDeptName(creator);
        articleEntity.setGmtCreate(new Date());
        articleEntity.setAuthor(deptUser.getUsername());
        articleEntity.setDeptId(deptUser.getDeptId());
        articleEntity.setCreator(creator);
        articleService.save(articleEntity);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ArticleEntity articleEntity){
        articleEntity.setGmtModified(new Date());
        articleService.updateById(articleEntity);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        articleService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
