package com.zxj.finalexam.utils;

import lombok.Data;

import java.util.List;

@Data
public class Node {
    private Long id;
    private Long parentId;
    private Object object;
    private List<Node> children;
}

