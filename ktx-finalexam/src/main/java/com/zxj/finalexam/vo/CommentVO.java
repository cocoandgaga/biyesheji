package com.zxj.finalexam.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zxj.finalexam.entity.Comment;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Accessors(chain = true)
public class CommentVO {
    private Long id;
    private Integer parentId;
    private Integer type;
    private String content;
    private Long commentator;
    private String creatorName;
    private Date gmtCreate;
    private Long likeCnt;
    private Long commentCnt;
    private List<Comment> childComments;
    private boolean show=false;
}
