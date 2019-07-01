package com.github.hollykunge.security.entity;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName Comment
 * @Description 评论
 * @Author hollykunge
 * @Date 2019/6/29 23:26
 * @Version 1.0
 **/
@Data
public class Comment {
    private Integer id;
    /**
     * uuid
     */
    private String commentId;
    /**
     * 属于哪个问题
     */
    private String feedBackId;
    private String content;
    private Date crtTime;
    private Date updTime;
    private String crtUser;
    private String updUser;
}
