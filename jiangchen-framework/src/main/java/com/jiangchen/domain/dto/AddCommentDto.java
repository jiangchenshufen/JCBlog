package com.jiangchen.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCommentDto {
    //文章id
    private Long articleId;
    //评论类型
    private String type;
    //根评论id
    private Long rootId;
    //回复目标评论id
    private Long toCommentId;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    //评论内容
    private String content;
}
