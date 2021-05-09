package com.zxj.finalexam.controller;

import com.zxj.finalexam.dao.DeptUserDao;
import com.zxj.finalexam.entity.DeptUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class  TelnetController {

    @Autowired
    private DeptUserDao deptUserDao;

    @RequestMapping("/telnet/insert/dept-user")
    public void insertDept(@RequestParam("deptId") Long deptId, @RequestParam("userId") Long userId){
        DeptUserEntity deptUserEntity=new DeptUserEntity();
        deptUserEntity.setDeptId(deptId);
        deptUserEntity.setUserId(userId);
        deptUserDao.insert(deptUserEntity);
    }

}
