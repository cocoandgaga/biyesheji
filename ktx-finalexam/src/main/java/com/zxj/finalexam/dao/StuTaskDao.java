package com.zxj.finalexam.dao;

import com.zxj.finalexam.entity.StuTaskEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxj.finalexam.vo.TaskCard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-04-12 19:44:13
 */
@Mapper
public interface StuTaskDao extends BaseMapper<StuTaskEntity> {

    /** 清除卡片下任务的开始结束时间 */
    void clearStartEndTime(String stuTaskId);
    TaskCard getTaskAndPosition(@Param("stuTaskId") String stuTaskId);

    int removeTaskFreeColumnMessagesById(String stuTaskId);
}
