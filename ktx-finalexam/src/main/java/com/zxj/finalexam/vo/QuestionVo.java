package com.zxj.finalexam.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionVo {
    private Long id;
    private String title;
    private String description;
    private List<String> tags=new ArrayList<>();
    private String content;
}
