package com.zxj.finalexam.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CourseScoreDTO {
    private String subjectName;
    private BigDecimal sumScore;

    /*private String deptName;
    private String stuName;
    private String taskName;
    private Integer score;*/

}
