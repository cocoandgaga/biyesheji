package com.zxj.finalexam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.Query;
import com.zxj.finalexam.dao.StuUserDao;
import com.zxj.finalexam.entity.StuUserEntity;
import com.zxj.finalexam.service.StuUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Map;
import java.util.Objects;


@Service("stuUserService")
public class StuUserServiceImpl extends ServiceImpl<StuUserDao, StuUserEntity> implements StuUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params,Long deptId) {
        IPage<StuUserEntity> page = this.page(
                new Query<StuUserEntity>().getPage(params),
                new QueryWrapper<StuUserEntity>().eq("OBJ_STATUS",0).eq("DEPT_ID",deptId)
        );

        return new PageUtils(page);
    }

    @Override
    public StuUserEntity queryByUserName(String username) {

        StuUserEntity stuUserEntity = query().eq("USERNAME", username).one();
        if (Objects.nonNull(stuUserEntity)){
            return stuUserEntity;
        }else{
            return null;
        }

    }

    @Override
    public void saveUser(StuUserEntity user) {
        user.setCreateTime(new Date());
        user.setObjStatus(0);
        //TODO 老师创建
        //user.setCreateUserId("");
        //sha256加密
        //是哪个系的学生
        //user.setDeptId("");
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
        user.setSalt(salt);
        this.save(user);

    }

    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {

        StuUserEntity userEntity = new StuUserEntity();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
                new QueryWrapper<StuUserEntity>().eq("USER_ID", userId).eq("PASSWORD", password));

    }


}
