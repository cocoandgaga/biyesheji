package com.zxj.finalexam.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxj.finalexam.entity.CardMessageEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CardMessageDao  extends BaseMapper<CardMessageEntity> {

}
