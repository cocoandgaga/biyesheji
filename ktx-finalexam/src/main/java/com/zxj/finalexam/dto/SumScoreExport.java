package com.zxj.finalexam.dto;

import lombok.Data;

@Data
public class SumScoreExport {
    private String subjectName;
    private String stuNo;
    private String stuName;
    private Integer sumScore;
}
