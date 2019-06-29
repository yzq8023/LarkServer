package com.github.hollykunge.security.entity;

import lombok.Data;

/**
 * @ClassName Comment
 * @Description 评论
 * @Author hollykunge
 * @Date 2019/6/29 23:26
 * @Version 1.0
 **/
@Data
public class Comment {
    private String id;
    private String toWhere;
    private String comment;
    private String userId;
}
