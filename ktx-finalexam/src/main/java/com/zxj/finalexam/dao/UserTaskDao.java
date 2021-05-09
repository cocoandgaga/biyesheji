package com.zxj.finalexam.dao;

import com.zxj.finalexam.entity.UserTaskEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxj.finalexam.vo.BoardVo;
import com.zxj.finalexam.vo.UserStoryCard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-04-12 19:44:13
 */
@Mapper
public interface UserTaskDao extends BaseMapper<UserTaskEntity> {
   List<UserStoryCard> queryByBoardVo(BoardVo boardVo);
}
