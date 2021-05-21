package com.zxj.finalexam.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxj.finalexam.dto.CompleteStatusDTO;
import com.zxj.finalexam.entity.CompleteStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompleteStatusDao extends BaseMapper<CompleteStatus> {
    List<CompleteStatusDTO> getCompleted(@Param("userTaskId") String userTaskId, @Param("deptId") Long deptId);
}
