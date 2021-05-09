package com.zxj.finalexam.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Accessors(chain = true)
public class BoardVo {
    private Long deptId;
    private String subjectName;
    private Long stuId;
}

