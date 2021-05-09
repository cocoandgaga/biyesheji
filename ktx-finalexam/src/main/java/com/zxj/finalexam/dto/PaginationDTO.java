package com.zxj.finalexam.dto;

import lombok.Data;

@Data
public class PaginationDTO {
    private Integer pageSize;
    private Integer pageIndex;
    private Long questionId;


}
