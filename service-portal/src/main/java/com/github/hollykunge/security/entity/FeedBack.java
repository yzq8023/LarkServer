package com.github.hollykunge.security.entity;

import lombok.Data;

import java.util.Date;

/**
 *@ClassName FeedBack
 *@Description 问题反馈
 *@Author hollykunge
 *@Date
 *@Version 1.0
 **/
@Data
public class FeedBack {
    private Integer id;
    /**
     * uuid
     */
    private String feedBackId;
    private String title;
    private String content;
    /**
     * 问题状态，true打开，false关闭
     */
    private Boolean status;
    /**
     * 问题类型
     */
    private String type;
    private String userId;
    private Date crtTime;
    private Date updTime;
    private String crtUser;
    private String updUser;
    /**
     * 普通用户可见性
     */
    private Boolean visible;
}
