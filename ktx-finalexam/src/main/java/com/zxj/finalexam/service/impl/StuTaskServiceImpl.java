package com.zxj.finalexam.service.impl;

import com.zxj.finalexam.entity.FreeColumnItem;
import com.zxj.finalexam.entity.StuUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxj.common.utils.PageUtils;
import com.zxj.common.utils.Query;

import com.zxj.finalexam.dao.StuTaskDao;
import com.zxj.finalexam.entity.StuTaskEntity;
import com.zxj.finalexam.service.StuTaskService;


@Service("stuTaskService")
public class StuTaskServiceImpl extends ServiceImpl<StuTaskDao, StuTaskEntity> implements StuTaskService {


}