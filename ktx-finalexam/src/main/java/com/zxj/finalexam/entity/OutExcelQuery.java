package com.zxj.finalexam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OutExcelQuery {
    //起始行
    private int rowStart;
    //结束行
    private int rowEnd;
    //起始列
    private int colStart;
    //结束列
    private int colEnd;
    //下拉参数
    private String[] params;
}
