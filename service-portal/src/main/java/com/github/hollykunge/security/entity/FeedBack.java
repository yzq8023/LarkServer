package com.github.hollykunge.security.entity;

import lombok.Data;

/**
 *@ClassName FeedBack
 *@Description 问题反馈
 *@Author hollykunge
 *@Date
 *@Version 1.0
 **/
@Data
public class FeedBack {
    private String id;
    private String title;
    private String description;
    /**
     * 问题状态，true打开，false关闭
     */
    private Boolean status;
    /**
     * 问题类型
     */
    private String type;
    /**
     * 赞同数量
     */
    private Integer likeNum;
    /**
     * 反对数量
     */
    private Integer dislikeNum;
    private String userId;
}
