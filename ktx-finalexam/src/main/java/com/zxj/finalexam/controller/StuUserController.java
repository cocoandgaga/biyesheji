package com.zxj.finalexam.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zxj.common.form.SysLoginForm;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.R;
import com.zxj.common.utils.TokenGenerator;
import com.zxj.finalexam.dao.CategoryDao;
import com.zxj.finalexam.dao.DeptUserDao;
import com.zxj.finalexam.dao.StuUserDao;
import com.zxj.finalexam.dto.PasswordDTO;
import com.zxj.finalexam.dto.StuUserDTO;
import com.zxj.finalexam.entity.CategoryEntity;
import com.zxj.finalexam.entity.DeptUserEntity;
import com.zxj.finalexam.entity.StuUserEntity;
import com.zxj.finalexam.entity.SysUserTokenEntity;
import com.zxj.finalexam.inf.ITelnetLoginService;
import com.zxj.finalexam.service.CategoryService;
import com.zxj.finalexam.service.DeptUserService;
import com.zxj.finalexam.service.StuUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-03-10 00:50:14
 */
@RestController
@RequestMapping("finalexam/stuuser")
public class StuUserController {
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DeptUserDao deptUserDao;
    @Autowired
    private StuUserService stuUserService;
    @Autowired
    private ITelnetLoginService telnetLoginService;
    @Autowired
    private DeptUserService deptUserService;
    @Autowired
    private StuUserDao stuUserDao;

    @PostMapping("/login")
    public R login(@RequestBody SysLoginForm form) {
        String captcha = telnetLoginService.validate(form);
        if (captcha.equals("false")) {
            return R.error("验证码不正确");
        } else if (captcha.equals("true")) {
            //用户信息
            StuUserEntity user = stuUserService.queryByUserName(form.getUsername());

            //账号不存在、密码错误
            if (user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
                return R.error("账号或密码不正确");
            }

            //账号锁定
            if (user.getStatus() == 0) {
                return R.error("账号已被锁定,请联系管理员");
            }

            //生成token，并保存到数据库

            return telnetLoginService.createToken(user.getUserId());
        } else {
            return R.error("StuUserController远程调用出错");
        }
    }


    @RequestMapping("/logout")
    public R logout(HttpServletRequest request) {
        StuUserEntity user = (StuUserEntity) request.getSession().getAttribute("user");
        if (user != null) {
            //生成一个token
            String token = TokenGenerator.generateValue();

            //修改token
            SysUserTokenEntity tokenEntity = new SysUserTokenEntity();
            tokenEntity.setStuId(user.getUserId());
            tokenEntity.setToken(token);
            stuUserDao.updateToken(tokenEntity);
           return R.ok();
        }else{
            return R.error("未登录");
        }
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("creator");
        DeptUserEntity dept = deptUserDao.getDeptName(userId);
        PageUtils page = stuUserService.queryPage(params, dept.getDeptId());
        if (page.getList() != null) {
            List<StuUserEntity> stus = (List<StuUserEntity>) page.getList();
            stus.forEach(stuUserEntity -> stuUserEntity.setDeptName(dept.getName()));
        }
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R stuInfo(@PathVariable("id") Integer id) {
        StuUserEntity stu = stuUserService.getById(id);
        CategoryEntity dept = categoryDao.selectOne(new QueryWrapper<CategoryEntity>().eq("cat_id", stu.getDeptId()));
        StuUserDTO user = new StuUserDTO();
        BeanUtils.copyProperties(stu, user);
        user.setDeptName(dept.getName());
        List<Long> catIds = categoryService.getdeptCase(dept.getCatId());
        return R.ok().put("user", user).put("categroyIds", catIds);
    }

    @RequestMapping("/info")
    public R info(HttpServletRequest request) {
        StuUserEntity stu = (StuUserEntity) request.getSession().getAttribute("user");
        DeptUserEntity deptUserEntity = deptUserDao.getDeptName(stu.getUserId());
        stu.setDeptName(deptUserEntity.getName());
        return R.ok().put("user", stu);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody StuUserEntity user) {
        stuUserService.save(user);
        return R.ok();
    }

    @RequestMapping("/batch/generator")
    public R batchGenerator(@RequestParam("stuofnum") Long stuofnum, HttpServletRequest request, @RequestParam("deptId") Long deptId) {
        Long creator = (Long) request.getSession().getAttribute("creator");
        if (creator != null) {
            Calendar calendar = Calendar.getInstance();
            List<StuUserEntity> stus = new ArrayList<>();
            String year = String.valueOf(calendar.get(Calendar.YEAR));
            String month = String.valueOf(calendar.get(Calendar.MONDAY));
            String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            String username = year + month + day + creator + deptId;
            for (int i = 10; i < stuofnum + 10; i++) {
                StuUserEntity stu = new StuUserEntity();
                stu.setCreateTime(new Date());
                stu.setCreateUserId(Long.valueOf(creator));
                stu.setUsername(username + i);
                stu.setStatus(1);
                stu.setDeptId(deptId);
                stu.setPassword("stu" + username + i);
                //sha256加密
                String salt = RandomStringUtils.randomAlphanumeric(20);
                stu.setPassword(new Sha256Hash(stu.getPassword(), salt).toHex());
                stu.setSalt(salt);
                stus.add(stu);
            }
            stuUserService.saveBatch(stus);
            return R.ok();

        } else {
            return R.error("未登录，请登录！！");
        }
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody StuUserEntity user) {
        user.setUpdateTime(new Date());
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
        user.setSalt(salt);
        stuUserService.updateById(user);
        return R.ok();
    }

    @RequestMapping("/modify-password")
    public R updatePassword(@RequestBody PasswordDTO passwordDTO, HttpServletRequest request) {
        StuUserEntity user = (StuUserEntity) request.getSession().getAttribute("user");
        if (user == null) return R.error("请登录...");
        if (!user.getUserId().equals(passwordDTO.getUserId())) return R.error("无修改权限");
        //sha256加密
        String password = new Sha256Hash(passwordDTO.getOldPassword(), user.getSalt()).toHex();
        //sha256加密
        String newPassword = new Sha256Hash(passwordDTO.getNewPassword(), user.getSalt()).toHex();

        //更新密码
        boolean flag = stuUserService.updatePassword(user.getUserId(), password, newPassword);
        if (!flag) {
            return R.error("原密码不正确");
        }
        return R.ok();
    }

    @RequestMapping("/modify-stuInfo")
    public R updateStuInfo(@RequestBody StuUserEntity user, HttpServletRequest request) {
        StuUserEntity stu = (StuUserEntity) request.getSession().getAttribute("user");
        if (stu == null) return R.error("请登录");
        if (!stu.getUserId().equals(user.getUserId())) return R.error("无权限..");
        user.setUpdateTime(new Date());
        stuUserService.updateById(user);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids) {
        stuUserService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
