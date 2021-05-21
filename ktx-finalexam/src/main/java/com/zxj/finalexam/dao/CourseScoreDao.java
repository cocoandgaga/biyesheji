package com.zxj.finalexam.dao;

import com.zxj.finalexam.dto.CourseScoreDTO;
import com.zxj.finalexam.dto.DetailExport;
import com.zxj.finalexam.dto.SumScoreExport;
import com.zxj.finalexam.entity.CourseScoreEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ZengXiaoJiao
 * @email one1.fifth5@gmail.com
 * @date 2021-05-16 23:04:59
 */
@Mapper
public interface CourseScoreDao extends BaseMapper<CourseScoreEntity> {

    List<CourseScoreDTO> getAllCourseScore(Long stuId, Long deptId);

    List<CourseScoreEntity> getDetailScore(Long deptId, Long stuId, String subjectName);

    List<DetailExport> getAllStudentScore(Map<String,Object> map);

    List<SumScoreExport> getAllStudentSumScore(Map<String,Object> map);
}
